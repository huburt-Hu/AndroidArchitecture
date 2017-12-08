package com.huburt.architecture.mvvm;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/7.
 */

public interface ActionCreator {

    void setAction(int actionId, Object... args);
}
