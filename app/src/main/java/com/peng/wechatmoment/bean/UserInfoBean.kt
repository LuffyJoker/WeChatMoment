package com.peng.wechatmoment.bean

import com.google.gson.annotations.SerializedName

/**
 * author : Mr.Q
 * e-mail : m1838044925_1@163.com
 * date   : 2019/11/28 23:12
 * desc   : 用户信息
 */
class UserInfoBean {
    /**
     * profile-image : http://img2.findthebest.com/sites/default/files/688/media/images/Mingle_159902_i0.png
     * avatar : http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png
     * nick : John Smith
     * username : jsmith
     */
    @SerializedName("profile-image")
    var profileimage: String? = null
    var avatar: String? = null
    var nick: String? = null
    var username: String? = null

}