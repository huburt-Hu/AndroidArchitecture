package com.huburt.app.mvvm.v.fragment.home;

import android.os.Bundle;

import com.huburt.app.R;
import com.huburt.app.base.BaseFragment;

/**
 * Created by hubert
 * <p>
 * Created on 2017/11/30.
 */

public class RecommendFragment extends BaseFragment {

    public static RecommendFragment newInstance() {
        Bundle args = new Bundle();
        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int provideLayout() {
        return R.layout.fragment_recommend;
    }


}
