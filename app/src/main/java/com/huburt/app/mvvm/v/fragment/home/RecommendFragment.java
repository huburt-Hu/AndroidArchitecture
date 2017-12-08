package com.huburt.app.mvvm.v.fragment.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huburt.app.R;
import com.huburt.app.base.BaseFragment;
import com.huburt.app.entity.RecommendMultiItem;
import com.huburt.app.mvvm.v.activity.VideoActivity;
import com.huburt.app.mvvm.v.adapter.RecommendMultiItemAdapter;
import com.huburt.app.mvvm.vm.RecommendViewModel;
import com.huburt.architecture.mvvm.ActionObserver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hubert
 * <p>
 * Created on 2017/11/30.
 */

public class RecommendFragment extends BaseFragment {

    @BindView(R.id.tv_top)
    TextView tvTop;
    @BindView(R.id.tv_label)
    TextView tvLabel;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private RecommendViewModel viewModel;
    private RecommendMultiItemAdapter mAdapter;

    private int page = 0;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(RecommendViewModel.class);
        viewModel.getRecommendLiveData().observe(this, new ActionObserver<List<RecommendMultiItem>>() {
            @Override
            public void onAction(int id, Object... args) {
                if (id == 2) {
                    Toast.makeText(getContext(), (String) args[0], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChanged(@Nullable List<RecommendMultiItem> recommendMultiItems) {
                refreshLayout.setRefreshing(false);
                mAdapter.loadMoreComplete();
                if (page == 0) {
                    mAdapter.setNewData(recommendMultiItems);
                    if (recommendMultiItems == null || recommendMultiItems.size() == 0) {
//                mAdapter.setEmptyView(getEmptyView());
                    }
                } else {
                    if (recommendMultiItems != null && recommendMultiItems.size() > 0) {
                        mAdapter.addData(recommendMultiItems);
                    } else {
                        mAdapter.loadMoreEnd(true);
                    }
                }
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(() -> {
            page = 0;
            viewModel.updatePage(getIdx(true), true, false);
        });

        mAdapter = new RecommendMultiItemAdapter(new ArrayList<>());
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(() -> {
            page++;
            viewModel.updatePage(getIdx(false), false, false);
        }, recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoActivity.start(getContext(), mAdapter.getData().get(position).getIndexDataBean().getParam());
            }
        });
        //在setLayoutManager之前调用无效
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == mAdapter.getData().size()) {
                    return 2;
                }
                return mAdapter.getData().get(position).getSpanSize();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        refreshLayout.setRefreshing(true);
        viewModel.updatePage(0, false, true);
    }

    public int getIdx(boolean refresh) {
        List<RecommendMultiItem> data = mAdapter.getData();
        if (data == null || data.size() == 0) {
            return 0;
        }
        int index = refresh ? 0 : data.size() - 1;
        return data.get(index).getIndexDataBean() == null ? 0 : data.get(index).getIndexDataBean().getIdx();
    }
}
