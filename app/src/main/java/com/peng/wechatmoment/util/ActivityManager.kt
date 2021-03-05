package com.peng.wechatmoment.util

import android.app.Activity

/**
 *    author : Mr.Q
 *    e-mail : m1838044925_1@163.com
 *    date   : 2019/11/28 19:39
 *    desc   : Activity管理类,用于统一管理 Activity
 */
object ActivityManager {
    var hashSet: HashSet<Activity> = HashSet()

    fun addActivity(activity: Activity) {
        try {
            hashSet.add(activity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun exit() {
        hashSet.forEach {
            try {
                it.finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeActivity(activity: Activity) {
        if (hashSet.contains(activity)) {
            hashSet.remove(activity)
        }
    }


}