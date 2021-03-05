package com.peng.wechatmoment.util.rx.obervable

import com.peng.wechatmoment.base.MVPProgressView
import io.reactivex.disposables.Disposables
import io.reactivex.observers.ResourceSingleObserver

/**
 *    author : Mr.Q
 *    e-mail : m1838044925_1@163.com
 *    date   : 2019/11/28 19:42
 *    desc   : 带加载框的观察者
 */
abstract class ProgressSingleObserver<T>(
    private val mvpProgressView: MVPProgressView?,
    private val showProgress: Boolean = true,
    private val hideProgress: Boolean = true
) : ResourceSingleObserver<T>() {

    override fun onStart() {
        add(Disposables.fromRunnable { mvpProgressView?.hideProgress() })
        if (showProgress) mvpProgressView?.showProgress()
    }

    override fun onSuccess(t: T) {
        if (hideProgress) mvpProgressView?.hideProgress()
    }

    override fun onError(e: Throwable) {
        mvpProgressView?.hideProgress()
    }
}