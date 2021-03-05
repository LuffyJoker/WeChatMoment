package com.peng.wechatmoment.base.presenter

import com.peng.wechatmoment.base.MVPView
import com.peng.wechatmoment.base.interactor.MVPInteractor
import io.reactivex.disposables.Disposable

/**
 * author : Mr.Q
 * e-mail : m1838044925_1@163.com
 * date   : 2019/11/28 23:35
 * desc   : 基础Presenter，持有View的引用、Interactor的引用
 */
abstract class BasePresenter<V : MVPView, I : MVPInteractor> constructor(
    protected var interactor: I
) : MVPPresenter<V, I> {

    var disposable: Disposable? = null

    protected var view: V? = null

    private val isViewAttached: Boolean get() = view != null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    fun cancelAllRequest() {
        disposable?.dispose()
    }
}