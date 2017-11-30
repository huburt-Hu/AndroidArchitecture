package com.huburt.app.mvvm.v.fragment.nav;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.huburt.app.R;
import com.huburt.app.base.BaseFragment;
import com.huburt.app.mvvm.v.fragment.home.MainHomeFragment;
import com.huburt.app.widget.BottomNavigationViewHelper;

import butterknife.BindView;

/**
 * Created by hubert
 * <p>
 * Created on 2017/11/30.
 */

public class NavHomeFragment extends BaseFragment {


    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.bnv)
    BottomNavigationView bnv;

    private BaseFragment[] fragments = new BaseFragment[1];

    public static NavHomeFragment newInstance() {
        Bundle args = new Bundle();
        NavHomeFragment fragment = new NavHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int provideLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initFragments() {
        MainHomeFragment mainHomeFragment = findChildFragment(MainHomeFragment.class);
        if (mainHomeFragment == null) {
            fragments[0] = MainHomeFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_container, 0, fragments);
        } else {
            fragments[0] = mainHomeFragment;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initFragments();
        BottomNavigationViewHelper.disableShiftMode(bnv);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        showHideFragment(fragments[0]);
                        break;
                }
                return true;
            }
        });
    }

}
