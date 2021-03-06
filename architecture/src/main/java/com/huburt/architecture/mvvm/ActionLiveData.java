package com.huburt.architecture.mvvm;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by hubert on 2017/12/6.
 * <p>
 * LiveData本身只能有一种泛型的数据，在（接口）数据返回时只能设置有值或者null来判断，
 * 无法传递其他信息，如需要提示网络，数据错误等情况，为每一种情况定义一个LiveData又太过于繁琐。
 * 基于以上考虑对LiveData进行扩展，使其支持传递自定义Action，
 * 通过调用{@code setAction(int id, Object... args)}发送事件。
 * 并在observe方法中传入{@link ActionObserver}用于接收action事件作出处理。
 * <pre>
 * actionLiveData.observe(this, new ActionObserver<Integer>() {
 *      {@literal @}Override
 *      public void onAction(int id, Object... args) {
 *          if (id == 1) {
 *              //do something
 *          }
 *      }
 *
 *      {@literal @}Override
 *      public void onChanged(@Nullable Integer integer) {
 *          //the original value
 *      }
 * });
 * </pre>
 */
public class ActionLiveData<T> extends MediatorLiveData<T> implements ActionCreator {

    private Set<ActionObserver<T>> actionObservers = new HashSet<>();
    private boolean active;

    private ActionEntity actionEntity;

    private Handler handler;
    private Runnable actionRun = new Runnable() {
        @Override
        public void run() {
            dispatchAction();
        }
    };

    /**
     * 通知Observer更新事件
     */
    private void dispatchAction() {
        if (active) {
            for (ActionObserver<T> actionObserver : actionObservers) {
                actionObserver.onAction(actionEntity.id, actionEntity.extra);
            }
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        active = true;
        for (Map.Entry<ActionLiveData<?>, ActionSource<?>> entry : mHandlers.entrySet()) {
            entry.getValue().plug();
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        active = false;
        for (Map.Entry<ActionLiveData<?>, ActionSource<?>> entry : mHandlers.entrySet()) {
            entry.getValue().unplug();
        }
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        super.observe(owner, observer);
        if (observer instanceof ActionObserver) {
            actionObservers.add((ActionObserver<T>) observer);
        }
    }

    @Override
    public void removeObserver(@NonNull Observer<T> observer) {
        super.removeObserver(observer);
        if (observer instanceof ActionObserver) {
            actionObservers.remove(observer);
        }
    }

    /**
     * 设置事件
     * @param id 事件id
     * @param args 可选的参数
     */
    @Override
    public void setAction(int id, Object... args) {
        actionEntity = new ActionEntity(id, args);
        if (isMainThread()) {
            dispatchAction();
        } else {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            handler.post(actionRun);
        }
    }

    public boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    /****支持Transformations的转换***/

    private Map<ActionLiveData<?>, ActionSource<?>> mHandlers = new HashMap<>();

    @Override
    public <S> void addSource(@NonNull LiveData<S> source, @NonNull Observer<S> onChanged) {
        super.addSource(source, onChanged);
        if (source instanceof ActionLiveData && onChanged instanceof ActionObserver) {
            addActionObserver((ActionLiveData<S>) source, (ActionObserver<S>) onChanged);
        }
    }

    protected <S> void addActionObserver(ActionLiveData<S> source, ActionObserver<S> actionObserver) {
        ActionSource<S> actionSource = new ActionSource<>(source, actionObserver);
        ActionSource<?> existing = mHandlers.put(source, actionSource);
        if (existing != null) {
            return;
        }
        if (hasActiveObservers()) {
            actionSource.plug();
        }
    }

    @Override
    public <S> void removeSource(@NonNull LiveData<S> toRemote) {
        super.removeSource(toRemote);
        if (toRemote instanceof ActionLiveData) {
            removeActionSource(toRemote);
        }
    }

    protected <S> void removeActionSource(@NonNull LiveData<S> toRemote) {
        ActionSource<?> source = mHandlers.remove(toRemote);
        if (source != null) {
            source.unplug();
        }
    }

    public static class ActionSource<T> implements ActionHandler {

        ActionLiveData<T> actionLiveData;
        ActionObserver<T> actionObserver;

        public ActionSource(ActionLiveData<T> actionLiveData, ActionObserver<T> actionObserver) {
            this.actionLiveData = actionLiveData;
            this.actionObserver = actionObserver;
        }

        void plug() {
            actionLiveData.observeForever(actionObserver);
        }

        void unplug() {
            actionLiveData.removeObserver(actionObserver);
        }

        @Override
        public void onAction(int id, Object... args) {
            actionObserver.onAction(id, args);
        }
    }
}
