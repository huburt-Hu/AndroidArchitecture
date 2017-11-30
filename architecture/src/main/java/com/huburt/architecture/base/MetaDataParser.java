package com.huburt.architecture.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by hubert
 * <p>
 * Created on 2017/11/28.
 * <p>
 *     获取manifest中meta-data信息
 * </p>
 */

public final class MetaDataParser {
    private static final String CONFIG = "config";

    public static void getConfig(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            appInfo.metaData.getString(CONFIG);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
