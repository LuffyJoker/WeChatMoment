package com.peng.wechatmoment.ui.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.blankj.utilcode.util.EncryptUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * author : Mr.Q
 * e-mail : m1838044925
 * date   : 2019/11/28 23:45
 * desc   : 磁盘缓存
 */
public class DiskCache implements ImageCache {
    private static DiskCache instance;
    private final File diskCacheDir;
    private Context context;

    private DiskCache(Context context) {
        this.context = context;
        diskCacheDir = FilePath.getDiskCacheDir(context, "bitmap");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs(); // 创建缓存目录
        }
    }

    public static DiskCache getInstance(Context context) {
        if (instance == null) {
            synchronized (DiskCache.class) {
                if (instance == null) {
                    instance = new DiskCache(context);
                }
            }
        }
        return instance;
    }

    @Override
    public Bitmap get(String url) {
        String key = EncryptUtils.encryptMD5ToString(url);
        return BitmapFactory.decodeFile(diskCacheDir + File.separator + key + ".jpg");
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        String key = EncryptUtils.encryptMD5ToString(url);
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(diskCacheDir + File.separator + key + ".jpg");
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
