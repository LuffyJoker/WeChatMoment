package com.peng.wechatmoment.util.rx

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer

/**
 *    author : Mr.Q
 *    e-mail : m1838044925_1@163.com
 *    date   : 2019/11/28 19:38
 *    desc   : 基类变换器
 */
abstract class BaseTransformer<T> protected constructor(
    private val subscribeOnScheduler: Scheduler,
    private val observeOnScheduler: Scheduler
) : SingleTransformer<T, T> {

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    }
}