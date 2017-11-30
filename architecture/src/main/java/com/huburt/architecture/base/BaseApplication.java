package com.huburt.architecture.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import timber.log.Timber;

/**
 * Created by hubert
 * <p>
 * Created on 2017/11/28.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.addLogAdapter(new AndroidLogAdapter());
        Timber.plant(new Timber.DebugTree() {
            @Override protected void log(int priority, String tag, String message, Throwable t) {
                Logger.log(priority, tag, message, t);
            }
        });
    }

    public interface AppLifecycleCallbacks {
        void attachBaseContext(Context base);

        void onCreate();

        void onConfigurationChanged(Configuration newConfig);

        void onLowMemory();

        void onTrimMemory(int level);
    }
}
