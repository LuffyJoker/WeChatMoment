package com.peng.wechatmoment.ui.wechatmoment

import com.peng.wechatmoment.base.MVPProgressView
import com.peng.wechatmoment.base.interactor.MVPInteractor
import com.peng.wechatmoment.base.presenter.MVPPresenter
import com.peng.wechatmoment.bean.UserInfoBean
import com.peng.wechatmoment.bean.WeChatMomentsItemBean
import io.reactivex.Single

interface WeChatMomentsContract {

    interface View : MVPProgressView {

        /**
         * 获取微信朋友圈列表，成功
         */
        fun getWeChatMomentsInfoSuccess(list: MutableList<WeChatMomentsItemBean>)

        /**
         * 获取微信朋友圈列表，失败
         */
        fun getWeChatMomentsInfoFail()

        fun getUserInfoSuccess(userInfoBean: UserInfoBean)

        fun getUserInfoFail()
    }

    interface Presenter<V : View, I : Interactor> : MVPPresenter<V, I> {

        /**
         * 获取微信朋友圈列表
         */
        fun getWeChatMomentsInfo()

        fun getUserInfo()

        fun getSumInfo()

    }

    interface Interactor : MVPInteractor {

        /**
         *  获取微信朋友圈列表
         */
        fun getWeChatMomentsInfo(): Single<MutableList<WeChatMomentsItemBean>>

        fun getUserInfo(): Single<UserInfoBean>

    }
}