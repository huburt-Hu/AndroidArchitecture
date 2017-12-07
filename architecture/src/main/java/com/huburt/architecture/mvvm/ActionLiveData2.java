package com.huburt.architecture.mvvm;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/7.
 */

public class ActionLiveData2<T> extends MediatorLiveData<ActionEntity<T>> implements ActionCreator {

    @Override
    public void setAction(int id, Object... args) {
        super.setValue(new ActionEntity<T>(id, args));
    }

    public void setValue_(T value) {
        super.setValue(new ActionEntity<T>(value));
    }

    public void postValue_(T value) {
        super.postValue(new ActionEntity<T>(value));
    }

    public void observe_(@NonNull LifecycleOwner owner, @NonNull ActionObserver2<T> observer) {
        super.observe(owner, observer);
    }

    public void observeForever_(@NonNull ActionObserver2<T> observer) {
        super.observeForever(observer);
    }

    public void removeObserver_(@NonNull ActionObserver2<T> observer) {
        super.removeObserver(observer);
    }

    @Nullable
    public T getValue_() {
        ActionEntity<T> entity = super.getValue();
        return entity == null ? null : entity.original;
    }


}
