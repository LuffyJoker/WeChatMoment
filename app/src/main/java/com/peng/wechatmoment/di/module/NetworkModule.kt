package com.peng.wechatmoment.di.module

import com.blankj.utilcode.util.LogUtils
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.peng.wechatmoment.BuildConfig
import com.peng.wechatmoment.base.network.ApiHelper
import com.peng.wechatmoment.constant.NamedConstant
import com.peng.wechatmoment.interceptor.LoggingInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 *    author : Mr.Q
 *    e-mail : m1838044925_1@163.com
 *    date   : 2019/11/29 20:51
 *    desc   : 网络模块
 */
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Named(NamedConstant.HTTP_LOGGING_INTERCEPTOR) httpLoggingInterceptor: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        builder.addInterceptor(httpLoggingInterceptor)

        return builder.build()
    }

    @Singleton
    @Provides
    @Named(NamedConstant.HTTP_LOGGING_INTERCEPTOR)
    fun provideHttpLoggingInterceptor(): Interceptor {
        val logger = object : LoggingInterceptor.Logger {
            override fun log(message: String) {
                LogUtils.d("OkHttp$message")
            }
        }
        return LoggingInterceptor(logger).apply { level = LoggingInterceptor.Level.BODY }
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory,
        gson:Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://thoughtworks-ios.herokuapp.com/")
            .client(okHttpClient)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()


    @Singleton
    @Provides
    fun provideApiHelper(retrofit: Retrofit): ApiHelper = retrofit.create(ApiHelper::class.java)
}