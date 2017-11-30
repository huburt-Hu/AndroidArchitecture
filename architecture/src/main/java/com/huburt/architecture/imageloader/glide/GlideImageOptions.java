package com.huburt.architecture.imageloader.glide;

import android.graphics.Bitmap;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.huburt.architecture.imageloader.BaseImageOptions;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/20.
 */

public class GlideImageOptions extends BaseImageOptions {
    private ImageReSize size = null; //重新设定容器宽高
    private boolean isCrossFade = false; //是否渐变平滑的显示图片
    private boolean isSkipMemoryCache = false; //是否跳过内存缓存

    private ViewPropertyAnimation.Animator animator; // 图片加载动画
    private Transformation<Bitmap>[] transformations;

    private GlideImageOptions(ImageReSize resize, int placeHolder, int errorDrawable,
                              boolean isCrossFade, boolean isSkipMemoryCache,
                              ViewPropertyAnimation.Animator animator,
                              Transformation<Bitmap>[] transformations) {
        this.placeHolder = placeHolder;
        this.size = resize;
        this.error = errorDrawable;
        this.isCrossFade = isCrossFade;
        this.isSkipMemoryCache = isSkipMemoryCache;
        this.animator = animator;
        this.transformations = transformations;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public ImageReSize getSize() {
        return size;
    }

    public boolean isCrossFade() {
        return isCrossFade;
    }

    public boolean isSkipMemoryCache() {
        return isSkipMemoryCache;
    }

    public ViewPropertyAnimation.Animator getAnimator() {
        return animator;
    }

    public Transformation<Bitmap>[] getTransformations() {
        return transformations;
    }

    public class ImageReSize {
        public int reWidth = 0;
        public int reHeight = 0;

        public ImageReSize(int reWidth, int reHeight) {
            if (reHeight <= 0) {
                reHeight = 0;
            }
            if (reWidth <= 0) {
                reWidth = 0;
            }
            this.reHeight = reHeight;
            this.reWidth = reWidth;
        }
    }

    public static final class Builder {
        private int placeHolder = -1;
        private ImageReSize size = null;
        private int errorDrawable = -1;
        private boolean isCrossFade = false;
        private boolean isSkipMemoryCache = false;
        private ViewPropertyAnimation.Animator animator = null;
        private Transformation<Bitmap>[] transformations = null;

        public Builder placeHolder(int drawable) {
            this.placeHolder = drawable;
            return this;
        }

        public Builder reSize(ImageReSize size) {
            this.size = size;
            return this;
        }

        public Builder anmiator(ViewPropertyAnimation.Animator animator) {
            this.animator = animator;
            return this;
        }

        public Builder errorDrawable(int errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        public Builder isCrossFade(boolean isCrossFade) {
            this.isCrossFade = isCrossFade;
            return this;
        }

        public Builder isSkipMemoryCache(boolean isSkipMemoryCache) {
            this.isSkipMemoryCache = isSkipMemoryCache;
            return this;
        }

        @SafeVarargs
        public final Builder transformations(Transformation<Bitmap>... transformations) {
            this.transformations = transformations;
            return this;
        }

        public GlideImageOptions build() {
            return new GlideImageOptions(size, placeHolder, errorDrawable, isCrossFade,
                    isSkipMemoryCache, animator, transformations);
        }
    }
}