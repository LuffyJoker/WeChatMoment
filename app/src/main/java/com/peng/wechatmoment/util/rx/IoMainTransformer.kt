package com.peng.wechatmoment.util.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *    author : Mr.Q
 *    e-mail : m1838044925_1@163.com
 *    date   : 2019/11/28 19:39
 *    desc   : IO线程 切换到 main 线程
 */
class IoMainTransformer<T> : BaseTransformer<T>(Schedulers.io(), AndroidSchedulers.mainThread())