@file:Suppress("NOTHING_TO_INLINE")

package com.peng.wechatmoment.util.ext

import com.peng.wechatmoment.base.MVPProgressView
import com.peng.wechatmoment.base.OnErrorAction
import com.peng.wechatmoment.util.rx.obervable.ProgressCompletableObserver
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

inline fun Completable.ioToMainScheduler(): Completable =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

/**
 * 订阅，默认捕获异常(printStackTrace)，默认处理进度条显隐
 */
inline fun Completable.subscribeProgress(
    mvpProgressView: MVPProgressView?,
    onErrorAction: OnErrorAction? = null,
    showProgress: Boolean = true,
    hideProgress: Boolean = true,
    crossinline onError: (Throwable) -> Unit = {
        onErrorAction?.showErrorMsg(it.nonNullMsg())
            ?: it.printStackTrace()
    },
    crossinline onComplete: () -> Unit
): Disposable {
    return subscribeWith(object :
        ProgressCompletableObserver(mvpProgressView, showProgress, hideProgress) {

        override fun onComplete() {
            super.onComplete()
            onComplete.invoke()
        }

        override fun onError(e: Throwable) {
            super.onError(e)
            onError.invoke(e)
        }
    })
}