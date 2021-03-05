package com.peng.wechatmoment.base.network;

import android.annotation.TargetApi;
import android.os.Build;

import androidx.collection.ArrayMap;

import org.reactivestreams.Subscription;

import java.util.Set;

/**
 * author : Mr.Q
 * e-mail : m1838044925
 * date   : 2019/12/13 20:54
 * desc   :
 */
public class RxApiManager implements RxActionManager<Object> {

    private static RxApiManager sInstance = null;

    private ArrayMap<Object, Subscription> maps;

    public static RxApiManager getInstance() {
        if (sInstance == null) {
            synchronized (RxApiManager.class) {
                if (sInstance == null) {
                    sInstance = new RxApiManager();
                }
            }
        }
        return sInstance;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private RxApiManager() {
        maps = new ArrayMap<>();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void add(Object tag, Subscription subscription) {
        maps.put(tag, subscription);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void remove(Object tag) {
        if (!maps.isEmpty()) {
            maps.remove(tag);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void removeAll() {
        if (!maps.isEmpty()) {
            maps.clear();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override public void cancel(Object tag) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(tag) == null) {
            return;
        }
//        if (!maps.get(tag).isUnsubscribed()) {
//            maps.get(tag).unsubscribed();
//            maps.remove(tag);
//        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override public void cancelAll() {
        if (maps.isEmpty()) {
            return;
        }
        Set<Object> keys = maps.keySet();
        for (Object apiKey : keys) {
            cancel(apiKey);
        }
    }
}
