package com.peng.wechatmoment.ui.imageloader;

import android.graphics.Bitmap;

/**
 * author : Mr.Q
 * e-mail : m1838044925_1@163.com
 * date   : 2019/11/29 22:11
 * desc   : 抽象图片存取，如果要实现其他缓存图片的方式，实现该接口
 */
public interface ImageCache {
    Bitmap get(String url);

    void put(String url, Bitmap bitmap);
}
