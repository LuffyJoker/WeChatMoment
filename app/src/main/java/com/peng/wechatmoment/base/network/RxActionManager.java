package com.peng.wechatmoment.base.network;

import org.reactivestreams.Subscription;

/**
 * author : Mr.Q
 * e-mail : m1838044925
 * date   : 2019/12/13 20:55
 * desc   :
 */
public interface RxActionManager<T> {

    void add(T tag, Subscription subscription);

    void remove(T tag);

    void cancel(T tag);

    void cancelAll();
}