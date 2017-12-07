package com.huburt.architecture.mvvm;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/7.
 */

public class ActionEntity<T> {
    public static final int ACTION = 0x1;
    public static final int VALUE = 0x2;

    public int type;

    public int id;
    public Object[] extra;
    public T original;

    public ActionEntity(T original) {
        this.original = original;
        type = VALUE;
    }

    public ActionEntity(int id, Object[] extra) {
        this.id = id;
        this.extra = extra;
        type = ACTION;
    }

}
