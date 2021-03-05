package com.peng.wechatmoment.base.network

import com.peng.wechatmoment.bean.UserInfoBean
import com.peng.wechatmoment.bean.WeChatMomentsItemBean
import com.peng.wechatmoment.constant.UrlConstant
import io.reactivex.Single
import retrofit2.http.GET

/**
 * author : Mr.Q
 * e-mail : m1838044925_1@163.com
 * date   : 2019/11/29 21:27
 * desc   : 统一接口管理
 */
interface ApiHelper {

    @GET(UrlConstant.GET_MOMENTS_INFO)
    fun getWechatMomentsInfo(): Single<MutableList<WeChatMomentsItemBean>>

    @GET(UrlConstant.GET_USER_INFO)
    fun getUserInfo(): Single<UserInfoBean>

}