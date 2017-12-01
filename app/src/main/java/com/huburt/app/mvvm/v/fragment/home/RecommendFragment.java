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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huburt.app.R;
import com.huburt.app.base.BaseFragment;
import com.huburt.app.entity.RecommendMultiItem;
import com.huburt.app.mvvm.v.adapter.RecommendMultiItemAdapter;
import com.huburt.app.mvvm.vm.RecommendViewModel;

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
    private RecommendMultiItemAdapter adapter;

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
        viewModel.getRecommendLiveData().observe(this, new Observer<List<RecommendMultiItem>>() {
            @Override
            public void onChanged(@Nullable List<RecommendMultiItem> recommendMultiItems) {
                refreshLayout.setRefreshing(false);
                adapter.loadMoreComplete();
                if (page == 0) {
                    adapter.setNewData(recommendMultiItems);
                    if (recommendMultiItems == null || recommendMultiItems.size() == 0) {
//                mAdapter.setEmptyView(getEmptyView());
                    }
                } else {
                    if (recommendMultiItems != null && recommendMultiItems.size() > 0) {
                        adapter.addData(recommendMultiItems);
                    } else {
                        adapter.loadMoreEnd(true);
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

        adapter = new RecommendMultiItemAdapter(new ArrayList<>());
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> {
            page++;
            viewModel.updatePage(getIdx(false), false, false);
        }, recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        //在setLayoutManager之前调用无效
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == adapter.getData().size()) {
                    return 2;
                }
                return adapter.getData().get(position).getSpanSize();
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
        List<RecommendMultiItem> data = adapter.getData();
        if (data == null || data.size() == 0) {
            return 0;
        }
        int index = refresh ? 0 : data.size() - 1;
        return data.get(index).getIndexDataBean() == null ? 0 : data.get(index).getIndexDataBean().getIdx();
    }
}
