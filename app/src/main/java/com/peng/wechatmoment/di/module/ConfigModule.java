package com.peng.wechatmoment.di.module;

import android.app.Application;

import dagger.Module;

/**
 * author : joker.peng e-mail : joker@fiture.com date   : 2021/3/5 5:42 PM desc   :
 */
@Module
public class ConfigModule {
    private Application application;

    public ConfigModule(Application application) {
        this.application = application;
    }

}