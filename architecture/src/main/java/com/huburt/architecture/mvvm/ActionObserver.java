package com.huburt.architecture.mvvm;

import android.arch.lifecycle.Observer;

public interface ActionObserver<T> extends Observer<T>, ActionHandler {

}