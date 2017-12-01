package com.huburt.app.base;

import com.huburt.architecture.base.BaseApplication;
import com.huburt.architecture.imageloader.ImageLoaderManager;
import com.huburt.architecture.imageloader.glide.GlideImageLoaderStrategy;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/1.
 */

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderManager.getInstance().init(new GlideImageLoaderStrategy());
    }
}
