package com.peng.wechatmoment.util.rx

/**
 *    author : Mr.Q
 *    e-mail : m1838044925_1@163.com
 *    date   : 2019/11/28 19:37
 *    desc   : 线程切换器
 */
object TransformerFactory {
    fun <T> ioToMain(): IoMainTransformer<T> {
        return IoMainTransformer()
    }
}