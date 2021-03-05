package com.peng.wechatmoment.ui.wechatmoment

import com.blankj.utilcode.util.LogUtils
import com.peng.wechatmoment.base.presenter.BasePresenter
import com.peng.wechatmoment.util.ext.ioToMainScheduler
import com.peng.wechatmoment.util.ext.subscribeProgress
import io.reactivex.Completable
import javax.inject.Inject

class WechatMomentsPresenter<V : WeChatMomentsContract.View, I : WeChatMomentsContract.Interactor> @Inject constructor(
    interactor: I
) : BasePresenter<V, I>(interactor = interactor), WeChatMomentsContract.Presenter<V, I> {

    override fun getWeChatMomentsInfo() {
        interactor.getWeChatMomentsInfo()
            .ioToMainScheduler()
            .subscribeProgress(view,
                onError = {
                    view?.getWeChatMomentsInfoFail()
                },
                onSuccess = {
                    view?.getWeChatMomentsInfoSuccess(it)
                }
            )
    }

    override fun getUserInfo() {
        interactor.getUserInfo()
            .ioToMainScheduler()
            .subscribeProgress(view,
                onError = {
                    view?.getUserInfoFail()
                },
                onSuccess = {
                    view?.getUserInfoSuccess(it)
                }
            )
    }

    override fun getSumInfo() {
        val arrayComplete = arrayOf(
            interactor.getUserInfo()
                .ioToMainScheduler()
                .doOnSuccess {
                    view?.getUserInfoSuccess(it)
                }
                .doOnError {
                    view?.getUserInfoFail()
                }
                .ignoreElement(),
            interactor.getWeChatMomentsInfo()
                .ioToMainScheduler()
                .doOnSuccess {
                    view?.getWeChatMomentsInfoSuccess(it)
                }
                .doOnError {
                    view?.getWeChatMomentsInfoFail()
                }
                .ignoreElement()
        )
        Completable.mergeArray(*arrayComplete)
            .ioToMainScheduler()
            .subscribeProgress(view,
                onComplete = {
                    LogUtils.d("mergeArray 执行完成")
                })
    }

}

