package com.peng.wechatmoment.di.component;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * author : joker.peng e-mail : joker@fiture.com date   : 2021/3/5 2:08 PM desc   :
 */
@Singleton
public class User {
    private String name;

    @Inject
    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void callWork(String name) {
        Log.e("===z",name + "正在学习Dagger");
    }
}