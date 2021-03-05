package com.peng.wechatmoment.ui.wechatmoment

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.peng.wechatmoment.R
import com.peng.wechatmoment.base.activity.AbsDaggerActivity
import com.peng.wechatmoment.bean.MultipleItem
import com.peng.wechatmoment.bean.UserInfoBean
import com.peng.wechatmoment.bean.WeChatMomentsItemBean
import com.peng.wechatmoment.bean.dao.MomentsDAOBean
import com.peng.wechatmoment.constant.UrlConstant
import com.peng.wechatmoment.controller.DbController
import com.peng.wechatmoment.ui.imageloader.DoubleCache
import com.peng.wechatmoment.ui.imageloader.ImageLoader
import com.peng.wechatmoment.ui.wechatmoment.adapter.WeChatMomentAdapter
import com.peng.wechatmoment.util.ext.fromJson
import com.peng.wechatmoment.view.ActionSheetDialog
import com.peng.wechatmoment.view.LikePopupWindow
import com.peng.wechatmoment.view.OnPraiseOrCommentClickListener
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_wechat_moments.*
import kotlinx.android.synthetic.main.title.*
import javax.inject.Inject

class WechatMomentsActivity : AbsDaggerActivity(), WeChatMomentsContract.View {

    // 控制分页请求，因为数据接口未提供分页功能，所以在加载数据使，使用同一个接口，当条数大于 mTotalCount 时，不在加载更多的数据
    private var pageNow: Int = 1
    private val pageSize = 6
    private var mTotalCount: Long = 22

    // 朋友圈item集合
    private var itemList: MutableList<MultipleItem> = mutableListOf()

    // 朋友圈列表头
    private lateinit var headView: View

    // 评论弹窗
    private lateinit var likePopupWindow: LikePopupWindow

    // 朋友圈列表适配器
    private lateinit var mDataAdapter: WeChatMomentAdapter

    @Inject
    private lateinit var mPresenter: WeChatMomentsContract.Presenter<WeChatMomentsContract.View, WeChatMomentsContract.Interactor>

    override fun layoutId(): Int = R.layout.activity_wechat_moments

    override fun initView(savedInstanceState: Bundle?) {

        checkPermissionRequest(this)

        // 使用 SQLiteStudio 查看数据库的数据，此处朋友圈数据、用户信息采用数据库存储
//        SQLiteStudioService.instance().start(this)

        loadingLayout.showLoading()

        mPresenter.onAttach(this)

        loadingLayout.setOnRetryClickListener {
            loadingLayout.showLoading()
            loadData()
        }

        iv_back.setImageResource(R.mipmap.back)
        iv_back.setOnClickListener {
            ToastUtils.showShort("暂未实现任何功能")
        }

        tv_des.text = "朋友圈"
        iv_option.setImageResource(R.mipmap.camal)
        iv_option.setOnClickListener { showLogoSwitchWindow() }

        mDataAdapter = WeChatMomentAdapter(itemList)
        lv_moments.layoutManager = LinearLayoutManager(this)
        lv_moments.adapter = mDataAdapter
        initHeaderView()
        mDataAdapter.addHeaderView(headView)

        // 评论、点赞弹窗
        mDataAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.imgButton -> {
                    var itemBottomY = getCoordinateY(view) + view.height
                    likePopupWindow = LikePopupWindow(this, 0)
                    likePopupWindow.setOnPraiseOrCommentClickListener(object :
                        OnPraiseOrCommentClickListener {

                        override fun onPraiseClick(position: Int) {
                            ToastUtils.showShort("点赞成功")
                            likePopupWindow.dismiss()
                        }

                        override fun onCommentClick(position: Int) {
                            ll_comment.visibility = View.VISIBLE
                            et_comment.requestFocus()
                            likePopupWindow.dismiss()
                            et_comment.hint = "说点什么"
                            et_comment.setText("")
                            KeyboardUtils.showSoftInput(ll_comment)
                            view.postDelayed(Runnable {
                                val y = getCoordinateY(ll_comment)
                                //评论时滑动到对应item底部和输入框顶部对齐
                                lv_moments.smoothScrollBy(0, itemBottomY - y)
                            }, 300)
                        }
                    }).setTextView(0).setCurrentPosition(position)

                    if (likePopupWindow.isShowing) {
                        likePopupWindow.dismiss()
                    } else {
                        likePopupWindow.showPopupWindow(view)
                    }
                }
            }
        }

        lv_moments.setOnTouchListener(OnTouchListener { _, _ ->
            if (ll_comment.visibility == View.VISIBLE) {
                updateEditTextBodyVisible(View.GONE)
                return@OnTouchListener true
            }
            false
        })

        // 上拉加载数据

        mDataAdapter.loadMoreModule.setOnLoadMoreListener {
            if (NetworkUtils.isConnected()) {
                if (pageNow * pageSize < mTotalCount) {
                    pageNow++
                    loadMoreData()
                }
            } else {
                ToastUtils.showShort("当前无网络连接，请检查网络状况 ")
            }
        }
        mDataAdapter.loadMoreModule.isAutoLoadMore = true
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        mDataAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false


        //设置下拉刷新时的操作
        swipeRefreshLayout.setOnRefreshListener {
            if (NetworkUtils.isConnected()) {
                pageNow = 1
                itemList.clear()
                mPresenter.getSumInfo()
            } else {
                swipeRefreshLayout.isRefreshing = false
                ToastUtils.showShort("当前无网络连接，请检查网络状况 ")
            }
        }
    }

    /**
     * 获取控件左上顶点的Y坐标
     */
    private fun getCoordinateY(view: View): Int {
        val coordinate = IntArray(2)
        view.getLocationOnScreen(coordinate)
        return coordinate[1]
    }

    /**
     * 调增编辑框的显示状态
     */
    private fun updateEditTextBodyVisible(visibility: Int) {
        ll_comment.visibility = visibility
        if (View.VISIBLE == visibility) {
            ll_comment.requestFocus()
            //弹出键盘
            KeyboardUtils.showSoftInput(et_comment)
        } else if (View.GONE == visibility) { //隐藏键盘
            KeyboardUtils.hideSoftInput(et_comment)
        }
    }

    /**
     * 初始化数据
     */
    override fun initData(savedInstanceState: Bundle?) {
        loadData()
    }

    /**
     * 加载数据
     *      当有网络连接时，从服务器获取最新的数据
     *      当无网络连接时，从数据库读取 url 对应的缓存数据
     */
    private fun loadData() {
        if (NetworkUtils.isConnected()) {
            mPresenter.getSumInfo()
        } else {
            // 朋友圈信息
            val turnsType = object : TypeToken<MutableList<WeChatMomentsItemBean>>() {}.type
            var momentsDAOBean =
                DbController.getInstance(this).searchByWhere(UrlConstant.GET_MOMENTS_INFO)
            if (momentsDAOBean == null) {
                getWeChatMomentsInfoFail()
            } else {
                getWeChatMomentsInfoSuccess(
                    Gson().fromJson<MutableList<WeChatMomentsItemBean>>(
                        momentsDAOBean.json,
                        turnsType
                    )
                )
            }

            // 用户信息
            momentsDAOBean = DbController.getInstance(this).searchByWhere(UrlConstant.GET_USER_INFO)
            if (momentsDAOBean == null) {
                getUserInfoFail()
            } else {
                getUserInfoSuccess(Gson().fromJson<UserInfoBean>(momentsDAOBean.json))
            }
        }

    }

    /**
     * 上拉加载更多数据时，只获取朋友圈数据
     */
    private fun loadMoreData() {
        mPresenter.getWeChatMomentsInfo()
    }

    /**
     *  选择弹窗
     */
    private fun showLogoSwitchWindow() {

        ActionSheetDialog(this)
            .builder()
            .setCancelable(false)
            .setCanceledOnTouchOutside(false)
            .addSheetItem(
                "拍照",
                ActionSheetDialog.SheetItemColor.Blue,
                object : ActionSheetDialog.OnSheetItemClickListener {
                    override fun onClick(which: Int) {
                        ToastUtils.showShort("拍照")
                    }
                })
            .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue,
                object : ActionSheetDialog.OnSheetItemClickListener {
                    override fun onClick(which: Int) {
                        ToastUtils.showShort("选择照片")
                    }
                })
            .show()
    }


    /**
     * 朋友圈数据获取成功
     */
    override fun getWeChatMomentsInfoSuccess(list: MutableList<WeChatMomentsItemBean>) {

        // 更新数据库缓存的数据
        DbController.getInstance(this)
            .insertOrReplace(
                MomentsDAOBean(
                    null,
                    UrlConstant.GET_MOMENTS_INFO,
                    Gson().toJson(list)
                )
            )

        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }

        if (list.isEmpty()) {
            loadingLayout.showEmpty()
        } else {
            loadingLayout.showContent()
            for (obj in list) {
                if (obj.error == null && obj.getUnknowError() == null) {
                    itemList.add(MultipleItem(2, obj))
                }
            }

            lv_moments.adapter?.notifyDataSetChanged()
        }
    }

    /**
     * 获取数据失败
     */
    override fun getWeChatMomentsInfoFail() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
        loadingLayout.showError()
    }

    /**
     * 成功获取用户信息
     */
    override fun getUserInfoSuccess(userInfoBean: UserInfoBean) {

        // 更新或插入数据库
        DbController.getInstance(this).insertOrReplace(
            MomentsDAOBean(
                null,
                UrlConstant.GET_USER_INFO,
                Gson().toJson(userInfoBean)
            )
        )

        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }

        headView.findViewById<TextView>(R.id.nick_name)?.text = userInfoBean.nick
        ImageLoader.getInstance()!!
            .setImageCache(DoubleCache.getInstance(this)!!)
            .displayImage(
                "http://e.hiphotos.baidu.com/image/pic/item/2fdda3cc7cd98d10b510fdea233fb80e7aec9021.jpg",
                headView.findViewById<ImageView>(R.id.iv_user_bg)!!
            )
        ImageLoader.getInstance()!!
            .setImageCache(DoubleCache.getInstance(this)!!)
            .displayImage(
                "http://e.hiphotos.baidu.com/image/pic/item/2fdda3cc7cd98d10b510fdea233fb80e7aec9021.jpg",
                headView.findViewById<ImageView>(R.id.user_logo)!!
            )
    }

    override fun getUserInfoFail() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
        loadingLayout.showEmpty()
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun onDestroy() {
        mPresenter.onDetach()
//        SQLiteStudioService.instance().stop()
        super.onDestroy()
    }

    /**
     * 初始化朋友圈头部
     */
    private fun initHeaderView() {
        headView = LayoutInflater.from(this).inflate(R.layout.header, null) as View
    }

    /**
     * 检查权限，此处图片处理模块采用双缓存模式，即内存缓存和 SDCard 缓存方式，在使用SDCard缓存方式时，需要获取读写 SDCard 权限
     */
    private fun checkPermissionRequest(activity: FragmentActivity) {
        var permissions = RxPermissions(activity)
        permissions.setLogging(true);
        permissions.request(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).subscribe(Consumer<Boolean>() {
            LogUtils.d("checkPermission--:$it")
        })
    }
}
