package com.peng.wechatmoment.util.ext

import com.peng.wechatmoment.base.MVPProgressView
import com.peng.wechatmoment.base.OnErrorAction
import com.peng.wechatmoment.util.rx.TransformerFactory
import com.peng.wechatmoment.util.rx.obervable.ProgressSingleObserver
import io.reactivex.Single
import io.reactivex.disposables.Disposable


inline fun <T : Any> Single<T>.ioToMainScheduler(): Single<T> =
    compose(TransformerFactory.ioToMain<T>())


inline fun <T : Any> Single<T>.subscribeProgress(
    mvpProgressView: MVPProgressView?,
    onErrorAction: OnErrorAction? = null,
    showProgress: Boolean = true,
    hideProgress: Boolean = true,
    crossinline onError: (Throwable) -> Unit = {
        onErrorAction?.showErrorMsg(it.nonNullMsg())
            ?: it.printStackTrace()
    },
    crossinline onSuccess: (T) -> Unit
): Disposable {
    return subscribeWith(object :
        ProgressSingleObserver<T>(mvpProgressView, showProgress, hideProgress) {

        override fun onSuccess(t: T) {
            super.onSuccess(t)
            onSuccess.invoke(t)
        }

        override fun onError(e: Throwable) {
            super.onError(e)
            onError.invoke(e)
        }
    })
}