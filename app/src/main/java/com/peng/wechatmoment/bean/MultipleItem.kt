package com.peng.wechatmoment.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 *    author : Mr.Q
 *    e-mail : m1838044925_1@163.com
 *    date   : 2019/11/28 23:14
 *    desc   : 多布局实体
 */
class MultipleItem : MultiItemEntity {

    companion object {
        var TYPE_NEWS = 1
        var TYPE_IMAGES = 2
    }

    override var itemType = 0

    private var data: WeChatMomentsItemBean

    constructor(itemType: Int, data: WeChatMomentsItemBean) {
        this.itemType = itemType
        this.data = data
    }

    fun getData(): WeChatMomentsItemBean {
        return data
    }
}