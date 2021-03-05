package com.peng.wechatmoment.base.presenter

import com.peng.wechatmoment.base.MVPView
import com.peng.wechatmoment.base.interactor.MVPInteractor

/**
 * author : Mr.Q
 * e-mail : m1838044925_1@163.com
 * date   : 2019/11/28 23:37
 * desc   :  MVP分层架构: 基础Presenter接口
 */
interface MVPPresenter<in V : MVPView, in I : MVPInteractor> {

    /**
     * V : MVPView 已Attach
     */
    fun onAttach(view: V)

    /**
     * V : MVPView 已Detach
     */
    fun onDetach()
}