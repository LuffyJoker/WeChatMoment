package com.peng.wechatmoment.ui.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.blankj.utilcode.util.EncryptUtils;

/**
 * author : Mr.Q
 * e-mail : m1838044925
 * date   : 2019/11/28 23:41
 * desc   : 内存缓存
 */
public class MemoryCache implements ImageCache{

    private static MemoryCache instance;
    private LruCache<String, Bitmap> mMemoryCache;

    private MemoryCache() {
        //获取当前进程的可用内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //完成bitmap对象大小的计算
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    public static MemoryCache getInstance() {
        if (instance == null) {
            synchronized (MemoryCache.class) {
                if (instance == null) {
                    instance = new MemoryCache();
                }
            }
        }
        return instance;
    }

    @Override
    public Bitmap get(String url) {
        String key = EncryptUtils.encryptMD5ToString(url);
        return mMemoryCache.get(key);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        String key = EncryptUtils.encryptMD5ToString(url);
        mMemoryCache.put(key, bitmap);
    }
}
