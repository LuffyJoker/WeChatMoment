package com.peng.wechatmoment.base.activity

import android.os.Bundle
import com.peng.wechatmoment.util.ActivityManager
import dagger.android.AndroidInjection

/**
 * author : Mr.Q
 * e-mail : m1838044925_1@163.com
 * date   : 2019/11/28 23:25
 * desc   : 增加依赖注入
 */
abstract class AbsDaggerActivity : AbsBaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) // 依赖注入
        ActivityManager.addActivity(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        ActivityManager.removeActivity(this)
        super.onDestroy()
    }
}