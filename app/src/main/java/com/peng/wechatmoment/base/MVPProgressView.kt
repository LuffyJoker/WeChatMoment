package com.peng.wechatmoment.base

/**
 * author : Mr.Q
 * e-mail : m1838044925_1@163.com
 * date   : 2019/11/28 23:33
 * desc   :  MVP分层架构: 进度条View层，带有生命周期、进度条
 */
interface MVPProgressView : MVPLifecycleView {

    /**
     * 显示加载中
     */
    fun showProgress()

    /**
     * 隐藏加载中
     */
    fun hideProgress()
}