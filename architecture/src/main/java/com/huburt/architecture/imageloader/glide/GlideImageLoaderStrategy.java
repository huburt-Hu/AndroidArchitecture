package com.huburt.architecture.imageloader.glide;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huburt.architecture.imageloader.BaseImageOptions;
import com.huburt.architecture.imageloader.ImageLoader;

public class GlideImageLoaderStrategy implements ImageLoader {

    @Override
    public void load(View v, String url) {
        load(v, url, null);
    }

    @Override
    public void load(View v, String url, BaseImageOptions options) {
        if (v instanceof ImageView) {
            //将类型转换为ImageView
            ImageView imageView = (ImageView) v;
            //装配基本的参数
            DrawableTypeRequest dtr = Glide.with(imageView.getContext()).load(url);
            //装配附加参数
            loadOptions(dtr, options)
//                    .placeholder(R.drawable.placeholder)//默认图
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存策略-缓存全部
                    .thumbnail(0.3f)
                    .into(imageView);
        } else {
            throw new IllegalArgumentException("请传入ImageView或其子类！");
        }
    }

    @Override
    public void load(View v, int resId) {
        load(v, resId, null);
    }

    @Override
    public void load(View v, int drawable, BaseImageOptions options) {
        if (v instanceof ImageView) {
            ImageView imageView = (ImageView) v;
            DrawableTypeRequest dtr = Glide.with(imageView.getContext()).load(drawable);
            loadOptions(dtr, options)
//                    .placeholder(R.drawable.placeholder)//默认图
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存策略-缓存全部
                    .thumbnail(0.3f)
                    .into(imageView);
        } else {
            throw new IllegalArgumentException("请传入ImageView或其子类！");
        }
    }

    @Override
    public void clear() {

    }

    //这个方法用来装载由外部设置的参数
    private DrawableTypeRequest loadOptions(DrawableTypeRequest dtr, BaseImageOptions options) {
        if (options == null || !(options instanceof GlideImageOptions)) {
            return dtr;
        }
        GlideImageOptions o = (GlideImageOptions) options;
        if (o.getPlaceHolder() != -1)
            dtr.placeholder(o.getPlaceHolder());
        if (o.getError() != -1)
            dtr.error(o.getError());
        if (o.isCrossFade())
            dtr.crossFade();
        if (o.isSkipMemoryCache())
            dtr.skipMemoryCache(o.isSkipMemoryCache());
        if (o.getAnimator() != null)
            dtr.animate(o.getAnimator());
        if (o.getSize() != null)
            dtr.override(o.getSize().reWidth, o.getSize().reHeight);
        if (o.getTransformations() != null)
            dtr.bitmapTransform(o.getTransformations());
        return dtr;
    }
}