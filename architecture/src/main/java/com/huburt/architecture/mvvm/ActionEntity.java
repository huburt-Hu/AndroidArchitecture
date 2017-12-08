package com.huburt.architecture.mvvm;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/7.
 */

public class ActionEntity<T> {

    public int id;
    public Object[] extra;

    public ActionEntity(int id, Object[] extra) {
        this.id = id;
        this.extra = extra;
    }

}
