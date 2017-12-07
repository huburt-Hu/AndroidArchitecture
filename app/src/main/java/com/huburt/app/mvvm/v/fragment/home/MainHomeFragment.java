package com.huburt.app.mvvm.v.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.huburt.app.R;
import com.huburt.app.base.BaseFragment;
import com.huburt.app.mvvm.v.adapter.TabPagerAdapter;
import com.huburt.app.widget.TabLayoutIndicatorWidthHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hubert
 * <p>
 * Created on 2017/11/30.
 */

public class MainHomeFragment extends BaseFragment {

    @BindView(R.id.ll_tb_user)
    LinearLayout llTbUser;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.container)
    ViewPager viewPager;

    public static MainHomeFragment newInstance() {
        Bundle args = new Bundle();
        MainHomeFragment fragment = new MainHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int provideLayout() {
        return R.layout.fragment_main_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        List<Fragment> fragments = new ArrayList<Fragment>() {{
            add(LiveFragment.newInstance());
            add(RecommendFragment.newInstance());
        }};

        viewPager.setAdapter(new TabPagerAdapter(getChildFragmentManager(), fragments, new String[]{"直播", "推荐", "追番", "影视", "专栏"}));
        tabLayout.setupWithViewPager(viewPager);
        TabLayoutIndicatorWidthHelper.reflex(tabLayout);
    }
}
