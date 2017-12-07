package com.huburt.app.widget;

import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hubert on 2017/12/7
 * <p>
 * AppBarLayout没有提供临时取消滚动的api，因此写下这个Helper类，
 * 通过改变子view的layoutParams来实现代码临时关闭滚动以及恢复
 */

public final class AppBarLayoutScrollHelper {

    private AppBarLayoutScrollHelper() {
    }

    public static void disableScroll(AppBarLayout appBarLayout) {
        int childCount = appBarLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = appBarLayout.getChildAt(i);
            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
            if (AppBarLayout.LayoutParams.class.isInstance(layoutParams)) {
                AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) layoutParams;
                int original = params.getScrollFlags();
                childAt.setTag(original);//将原始flag存入tag
                params.setScrollFlags(0);//设置为默认值
                childAt.setLayoutParams(params);
            }
        }
    }

    public static void restoreScroll(AppBarLayout appBarLayout) {
        int childCount = appBarLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = appBarLayout.getChildAt(i);
            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
            if (AppBarLayout.LayoutParams.class.isInstance(layoutParams)) {
                Object tag = childAt.getTag();//取出tag中的原始flag
                if (tag == null || !(tag instanceof Integer)) {
                    continue;
                }
                AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) layoutParams;
                params.setScrollFlags((Integer) tag);
                childAt.setLayoutParams(params);
            }
        }
    }
}
