package com.huburt.architecture.imageloader;

import android.view.View;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/20.
 */

public class ImageLoaderManager implements ImageLoader {
    private ImageLoader imageLoader;

    private ImageLoaderManager() {
    }

    public static ImageLoaderManager getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final ImageLoaderManager sInstance = new ImageLoaderManager();
    }

    public void setImageLoader(ImageLoader loader) {
        if (loader != null)
            imageLoader = loader;
    }

    @Override
    public void load(View v, String url) {
        checkNull(imageLoader);
        imageLoader.load(v, url);
    }

    private void checkNull(ImageLoader imageLoader) {
        if (imageLoader == null) {
            throw new NullPointerException("please call ImageLoaderManager.setImageLoader(xx) in the application's onCreate");
        }
    }

    @Override
    public void load(View v, String url, BaseImageOptions options) {
        checkNull(imageLoader);
        imageLoader.load(v, url, options);
    }

    @Override
    public void load(View v, int resId) {
        checkNull(imageLoader);
        imageLoader.load(v, resId);
    }

    @Override
    public void load(View v, int resId, BaseImageOptions options) {
        checkNull(imageLoader);
        imageLoader.load(v, resId, options);
    }

    @Override
    public void clear() {
        checkNull(imageLoader);
        imageLoader.clear();
    }
}
