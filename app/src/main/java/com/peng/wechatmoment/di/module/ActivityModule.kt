package com.peng.wechatmoment.di.module

import com.peng.wechatmoment.ui.wechatmoment.WechatMomentsActivity
import com.peng.wechatmoment.ui.wechatmoment.WechatMomentsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *    author : Mr.Q
 *    e-mail : m1838044925_1@163.com
 *    date   : 2019/11/29 20:50
 *    desc   :
 */
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [(WechatMomentsModule::class)])
    abstract fun contributeWechatMomentsActivity(): WechatMomentsActivity
}