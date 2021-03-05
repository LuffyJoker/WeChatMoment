package com.peng.wechatmoment.util.ext

import com.google.gson.Gson

/**
 *    author : Mr.Q
 *    e-mail : m1838044925_1@163.com
 *    date   : 2019/11/30 18:58
 *    desc   :
 */

inline fun <reified T : Any> Gson.fromJson(json: String): T = this.fromJson(json, T::class.java)