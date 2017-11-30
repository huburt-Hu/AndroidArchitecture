package com.huburt.architecture.imageloader;

/**
 * Created by hubert
 * <p>
 * Created on 2017/11/28.
 */

public class BaseImageOptions {
    protected int placeHolder = -1; //当没有成功加载的时候显示的图片
    protected int error = -1;  //加载错误的时候显示

    public int getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(int placeHolder) {
        this.placeHolder = placeHolder;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
