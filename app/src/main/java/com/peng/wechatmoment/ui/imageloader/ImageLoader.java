package com.peng.wechatmoment.ui.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author : Mr.Q
 * e-mail : m1838044925
 * date   : 2019/11/28 23:40
 * desc   : 图片加载器
 */
public class ImageLoader {

    // 内存缓存为默认实现
    ImageCache mImageCache = MemoryCache.getInstance();

    private int sourceId;
    private static ImageLoader instance;
    private int MESSAGE_POST_RESULT = 1;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //更新 ImageView
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.imageView;
            if (imageView.getTag().equals(result.uri)) {
                if (result.bitmap == null) {
                    imageView.setImageResource(sourceId);
                } else {
                    imageView.setImageBitmap(result.bitmap);
                }
            }
        }
    };

    private ImageLoader() {

    }

    /**
     * 获取单例
     *
     * @return
     */
    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    /**
     * 设置缓存方式，默认为内存缓存（MemoryCache）
     *
     * @param imageCache
     */
    public ImageLoader setImageCache(ImageCache imageCache) {
        this.mImageCache = imageCache;
        return instance;
    }

    /**
     * 显示图片
     *
     * @param uri
     * @param imageView
     */
    public void displayImage(final String uri, final ImageView imageView) {
        Bitmap bitmap = mImageCache.get(uri);
        if (bitmap != null) { // 缓存中存在图片
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setTag(uri);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bmap = downLoadBitmapFromUrl(uri);
                LoaderResult loaderResult = new LoaderResult(imageView, uri, bmap);
                mMainHandler.obtainMessage(MESSAGE_POST_RESULT, loaderResult).sendToTarget();
                mImageCache.put(uri, bmap);
            }
        });
    }

    /**
     * 当没有获取到图片时，默认展示的图片
     *
     * @param sourceId
     */
    public ImageLoader defaultDisplay(int sourceId) {
        this.sourceId = sourceId;
        return getInstance();
    }

    /**
     * 返回结果的封装
     */
    private static class LoaderResult {
        public ImageView imageView;
        public String uri;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String uri, Bitmap bitmap) {
            this.imageView = imageView;
            this.uri = uri;
            this.bitmap = bitmap;
        }
    }

    /**
     * 下载图片
     *
     * @param imageUrl
     * @return
     */
    public Bitmap downLoadBitmapFromUrl(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5 * 1000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                connection.disconnect();
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
