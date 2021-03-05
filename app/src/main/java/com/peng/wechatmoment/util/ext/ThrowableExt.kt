package com.peng.wechatmoment.util.ext

/**
 *    author : Mr.Q
 *    e-mail : m1838044925
 *    date   : 2019/11/2820:48
 *    desc   :
 */

fun Throwable.nonNullMsg(): String = message ?: toString()