package com.peng.wechatmoment.ui.wechatmoment

import dagger.Module
import dagger.Provides

@Module
class WechatMomentsModule {

    @Provides
    fun provideWechatMomentsInteractor(weChatMomentInteractor: WechatMomentsInteractor): WeChatMomentsContract.Interactor =
        weChatMomentInteractor

    @Provides
    fun provideWechatMomentsPresenter(weChatMomentsPresenter: WechatMomentsPresenter<WeChatMomentsContract.View, WeChatMomentsContract.Interactor>)
            : WeChatMomentsContract.Presenter<WeChatMomentsContract.View, WeChatMomentsContract.Interactor> =
        weChatMomentsPresenter
}