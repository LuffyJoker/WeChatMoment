package com.peng.wechatmoment.base.activity

import android.app.Activity
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * author : Mr.Q
 * e-mail : m1838044925_1@163.com
 * date   : 2019/11/28 22:23
 * desc   : 基类 Activity，提供 view、data 初始化
 */
abstract class AbsBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(layoutId())

        initView(savedInstanceState)

        initData(savedInstanceState)
    }

    /**
     * 提供视图布局Id, 如果 [.initView] 返回 0, 框架则不会调用 [Activity.setContentView]
     * @return
     */
    @LayoutRes
    abstract fun layoutId(): Int


    /**
     * 初始化View
     *
     * @param savedInstanceState
     * @return
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    abstract fun initData(savedInstanceState: Bundle?)

}