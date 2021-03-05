package com.peng.wechatmoment.di.module

import android.app.Application
import android.content.Context
import com.peng.wechatmoment.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *    author : Mr.Q
 *    e-mail : m1838044925_1@163.com
 *    date   : 2019/11/29 21:16
 *    desc   :
 */
@Module(includes = [ActivityModule::class])
class AppModule constructor(var application: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application = application

}