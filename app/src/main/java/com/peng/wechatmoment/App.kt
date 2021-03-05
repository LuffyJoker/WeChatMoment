package com.peng.wechatmoment

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.peng.wechatmoment.di.component.User
import com.peng.wechatmoment.util.ScreenAdapterUtils
import com.peng.wechatmoment.util.ext.nonNullMsg
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

/**
 *    author : Mr.Q
 *    e-mail : m1838044925_1@163.com
 *    date   : 2019/11/28 20:41
 *    desc   :
 *    https://github.com/ogulcan/kotlin-mvp-dagger2
 */
class App : DaggerApplication() {

    companion object {
        private val TAG = App::class.java.simpleName
    }

    private var activityTasks: MutableList<Activity> = mutableListOf()

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    @Inject
    lateinit var mUser: User

    override fun onCreate() {
        super.onCreate()

        mUser.callWork("MengkZ")

        // util code
        // 尽早初始化该工具类框架，因为之后的初始化动作可能会用到该框架的部分工具类
        Utils.init(this)

        // 屏幕适配全局初始化
        ScreenAdapterUtils.setup(this)
        ScreenAdapterUtils.register(this, 360f, ScreenAdapterUtils.MATCH_BASE_WIDTH, ScreenAdapterUtils.MATCH_UNIT_DP)

        // global rxJava exception handler
        initRxJavaErrorHandler()

        // activity lifecycle callback
        initActivityLifecycle()

    }

    fun getActivityTasks(): MutableList<Activity> {
        return activityTasks
    }

    /**
     * 设置全局的RxJavaErrorHandler，避免了在RxJava调用链中出现致命错误时：App的Crash
     */
    private fun initRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler {
            LogUtils.d("$TAG:RxJavaErrorHandler", it.nonNullMsg())
        }
    }


    /**
     * 初始化activity生命周期回调，可以处理全局事件
     */
    private fun initActivityLifecycle() {
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
    }

    /**
     * 默认Activity生命周期回调函数，可用于添加全局行为
     */
    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            LogUtils.d("onCreated: " + activity.componentName.className)
            activityTasks.add(activity)
        }

        override fun onActivityStarted(activity: Activity) {
            LogUtils.d("onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            activityTasks.remove(activity)
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
//        return DaggerAppComponent.builder().create(this)
        return null
    }
}