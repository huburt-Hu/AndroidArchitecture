package com.huburt.app.mvvm.v.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.huburt.app.R;
import com.huburt.app.base.BaseActivity;
import com.huburt.app.mvvm.v.fragment.nav.NavHomeFragment;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity
        implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.nav)
    NavigationView nav;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.ll_theme)
    LinearLayout llTheme;
    @BindView(R.id.ll_change_skin)
    LinearLayout llChangeSkin;
    @BindView(R.id.drawer)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        StatusBarUtil.setColorForDrawerLayout(this, drawer, Color.TRANSPARENT);
        initNavigationView();
        llSetting.setOnClickListener(this);
        llChangeSkin.setOnClickListener(this);
        llTheme.setOnClickListener(this);
        initFragmentation();
    }

    private void initNavigationView() {
        removeNavigationViewScrollbar(nav);
        nav.setNavigationItemSelectedListener(this);
        nav.setCheckedItem(R.id.nav_home);
    }

    private void removeNavigationViewScrollbar(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    private void initFragmentation() {
        NavHomeFragment navHomeFragment = findFragment(NavHomeFragment.class);
        if (navHomeFragment == null) {
            loadRootFragment(R.id.fl_content, NavHomeFragment.newInstance());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_setting:

                break;
            case R.id.ll_theme:

                break;
            case R.id.ll_change_skin:

                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        nav.setCheckedItem(item.getItemId());
        switch (item.getItemId()) {
            case R.id.nav_home:
                NavHomeFragment navHomeFragment = findFragment(NavHomeFragment.class);
                if (navHomeFragment == null) {
                    start(NavHomeFragment.newInstance(), SupportFragment.SINGLETASK);
                } else {
                    popTo(NavHomeFragment.class, false);
                }
                break;
            case R.id.nav_history:
                break;
            case R.id.nav_offline_cache:
                break;
            case R.id.nav_my_collect:
                break;
            case R.id.nav_my_follow:
                break;
            case R.id.nav_look_later:
                break;
            case R.id.nav_live_center:
                break;
            case R.id.nav_my_vip:
                break;
            case R.id.nav_free_fow:
                break;
            case R.id.nav_vip_order:
                break;
        }
        return true;
    }
}
