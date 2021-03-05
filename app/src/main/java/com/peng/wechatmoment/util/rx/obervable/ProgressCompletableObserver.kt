package com.peng.wechatmoment.util.rx.obervable

import com.peng.wechatmoment.base.MVPProgressView
import io.reactivex.disposables.Disposables
import io.reactivex.observers.ResourceCompletableObserver

/**
 *    author : Mr.Q
 *    e-mail : m1838044925_1@163.com
 *    date   : 2019/11/30 4:37
 *    desc   :
 */
abstract class ProgressCompletableObserver(
    private val mvpProgressView: MVPProgressView?,
    private val showProgress: Boolean = true,
    private val hideProgress: Boolean = true
) : ResourceCompletableObserver() {

    override fun onStart() {
        add(Disposables.fromRunnable { mvpProgressView?.hideProgress() })
        if (showProgress) mvpProgressView?.showProgress()
    }

    override fun onComplete() {
        if (hideProgress) mvpProgressView?.hideProgress()
    }

    override fun onError(e: Throwable) {
        mvpProgressView?.hideProgress()
    }
}