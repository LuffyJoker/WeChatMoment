package com.peng.wechatmoment.ui.wechatmoment

import com.peng.wechatmoment.base.interactor.BaseInteractor
import com.peng.wechatmoment.base.network.ApiHelper
import com.peng.wechatmoment.bean.UserInfoBean
import com.peng.wechatmoment.bean.WeChatMomentsItemBean
import io.reactivex.Single
import javax.inject.Inject

class WechatMomentsInteractor @Inject constructor(private val apiHelper: ApiHelper) : BaseInteractor(),
    WeChatMomentsContract.Interactor {

    override fun getWeChatMomentsInfo(): Single<MutableList<WeChatMomentsItemBean>> {
        return apiHelper.getWechatMomentsInfo()
    }

    override fun getUserInfo(): Single<UserInfoBean> {
        return apiHelper.getUserInfo()
    }
}