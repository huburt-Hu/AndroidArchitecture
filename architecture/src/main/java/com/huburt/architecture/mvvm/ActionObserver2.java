package com.huburt.architecture.mvvm;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/7.
 */

public abstract class ActionObserver2<T> implements Observer<ActionEntity<T>>, ActionHandler {

    @Override
    public final void onChanged(@Nullable ActionEntity<T> entity) {
        if (entity != null) {
            if (entity.type == ActionEntity.VALUE) {
                onChanged_(entity.original);
            } else {
                onAction(entity.id, entity.extra);
            }
        }
    }

    public abstract void onChanged_(T entity);
}
