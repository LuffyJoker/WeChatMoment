package com.peng.wechatmoment.ui.wechatmoment.adapter

import android.widget.GridView
import android.widget.ImageView
import com.blankj.utilcode.util.SpanUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.peng.wechatmoment.R
import com.peng.wechatmoment.bean.MultipleItem
import com.peng.wechatmoment.ui.imageloader.ImageLoader

/**
 *    author : Mr.Q
 *    e-mail : m1838044925
 *    date   : 2019/11/28 23:01
 *    desc   : 朋友圈适配器
 */
class WeChatMomentAdapter(dataList: MutableList<MultipleItem>) :
    BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder>(dataList) {

    init {
        //必须绑定type和layout的关系
        addItemType(MultipleItem.TYPE_IMAGES, R.layout.moments_item)
        addItemType(MultipleItem.TYPE_NEWS, R.layout.news_item)

        addChildClickViewIds(R.id.imgButton)
    }

    override fun convert(helper: BaseViewHolder, item: MultipleItem) {
        when (helper.itemViewType) {

            // 朋友圈
            MultipleItem.TYPE_IMAGES -> {

                ImageLoader.getInstance()!!
                    .defaultDisplay(R.mipmap.ic_launcher)
                    .displayImage(
                        "http://e.hiphotos.baidu.com/image/pic/item/2fdda3cc7cd98d10b510fdea233fb80e7aec9021.jpg",
                        helper.getView<ImageView>(R.id.iv_item_portrait)
                    )

                helper.setText(R.id.name, item.getData().sender?.username)
                helper.setText(R.id.content, item.getData().content)
                var gridView: GridView = helper.getView(R.id.gridView)
                if (item.getData().images != null && item.getData().images?.size!! > 0) {
                    gridView.adapter = ImageAdapter(context, item.getData().images)
                } else {
                    gridView.adapter = null
                }

                if (item.getData().comments != null && item.getData().comments?.size!! > 0) {
                    helper.setVisible(R.id.ll_comment_container, true)
                    // 此处富文本显示评论
                    var spanUtils = SpanUtils()
                    item.getData().comments?.forEachIndexed { index, commentsBean ->
                        if (index == item.getData().comments?.size?.minus(1)) {
                            spanUtils.append(commentsBean.sender?.username.toString())
                                .setForegroundColor(0xff8080FF.toInt())
                                .append(" : ")
                                .append(commentsBean.content.toString())
                                .setForegroundColor(0xff2B2B2B.toInt())
                        } else {
                            spanUtils.append(commentsBean.sender?.username.toString())
                                .setForegroundColor(0xff8080FF.toInt())
                                .append(" : ")
                                .setForegroundColor(0xff8080FF.toInt())
                                .append(commentsBean.content.toString())
                                .setForegroundColor(0xff2B2B2B.toInt())
                                .append("\n")
                        }
                    }
                    helper.setText(R.id.commentary, spanUtils.create())
                } else {
                    helper.setGone(R.id.ll_comment_container, false)
                }

                item.getData().comments.apply {


                }
            }

            // 商品下面的子商品
            MultipleItem.TYPE_NEWS -> {

            }
        }
    }
}