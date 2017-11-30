package com.huburt.architecture.imageloader;

import android.view.View;

/**
 * Created by julie on 2017/2/17.
 */

public interface ImageLoader {
    void load(View v, String url);

    void load(View v, String url, BaseImageOptions options);

    void load(View v, int resId);

    void load(View v, int resId, BaseImageOptions options);

    void clear();
}
