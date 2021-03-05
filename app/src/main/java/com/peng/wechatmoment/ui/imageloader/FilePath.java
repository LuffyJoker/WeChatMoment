package com.peng.wechatmoment.ui.imageloader;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * author : Mr.Q
 * e-mail : m1838044925
 * date   : 2019/11/28 23:48
 * desc   : 图片缓存路径统一管理
 */
public class FilePath {
    public static File getDiskCacheDir(Context context, String cacheDir) {
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getParent();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + cacheDir);
    }
}
