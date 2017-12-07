package com.huburt.app.mvvm.v.fragment.home;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;

import com.huburt.app.R;
import com.huburt.app.base.BaseFragment;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/6.
 */

public class LiveFragment extends BaseFragment {

    public static LiveFragment newInstance() {
        Bundle args = new Bundle();
        LiveFragment fragment = new LiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int provideLayout() {
        return R.layout.fragment_live;
    }
}
