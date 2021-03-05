package com.peng.wechatmoment.base

/**
 * author : Mr.Q
 * e-mail : m1838044925_1@163.com
 * date   : 2019/11/28 23:34
 * desc   :  错误msg的统一交互
 */
interface OnErrorAction {

    /**
     * 显示 error msg
     */
    fun showErrorMsg(msg: CharSequence)
}