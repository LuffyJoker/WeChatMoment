package com.peng.wechatmoment.ui.imageloader;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * author : Mr.Q
 * e-mail : m1838044925
 * date   : 2019/11/29 23:03
 * desc   : 双缓存，内存缓存，磁盘缓存
 */
public class DoubleCache implements ImageCache {

    private static DoubleCache instance;

    private static ImageCache mMemoryCache;
    private static ImageCache mDiskCache;

    public static DoubleCache getInstance(Context context) {
        if (instance == null) {
            synchronized (DoubleCache.class) {
                if (instance == null) {
                    instance = new DoubleCache();
                    mMemoryCache = MemoryCache.getInstance();
                    mDiskCache = DiskCache.getInstance(context);
                }
            }
        }
        return instance;
    }

    private DoubleCache() {

    }

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap == null) {
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
        mDiskCache.put(url, bitmap);
    }
}
