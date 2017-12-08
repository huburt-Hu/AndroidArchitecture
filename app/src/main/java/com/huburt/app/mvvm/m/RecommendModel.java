package com.huburt.app.mvvm.m;

import com.huburt.app.api.Api;
import com.huburt.app.api.HomeService;
import com.huburt.app.entity.IndexData;
import com.huburt.app.entity.RecommendMultiItem;
import com.huburt.architecture.http.RetrofitFactory;
import com.huburt.architecture.mvvm.ActionLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/1.
 */

public class RecommendModel {

    private boolean isOdd = true;
    private ActionLiveData<List<RecommendMultiItem>> liveData = new ActionLiveData<>();

    public ActionLiveData<List<RecommendMultiItem>> getRecommendData(int idx, String pull, int login_event) {
        Timber.i("getRecommendData(%s,%s,%s)", idx, pull, login_event);
        HomeService homeService = RetrofitFactory.getDefaultRetrofit(Api.RECOMMEND_BASE_URL, null)
                .create(HomeService.class);
        homeService.getRecommendIndexData(idx, pull, login_event).enqueue(new Callback<IndexData>() {
            @Override
            public void onResponse(Call<IndexData> call, Response<IndexData> response) {
                List<RecommendMultiItem> items = parseIndexData(response.body());
                Timber.i("items:%s", items);
                liveData.setValue(items);
                liveData.setAction(2,"success");
            }

            @Override
            public void onFailure(Call<IndexData> call, Throwable t) {
                Timber.i("getRecommendData failed");
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    private List<RecommendMultiItem> parseIndexData(IndexData indexData) {
        List<RecommendMultiItem> list = new ArrayList<>();
        List<IndexData.DataBean> data = indexData.getData();
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                IndexData.DataBean dataBean = data.get(i);
                if (dataBean != null) {
                    String gotoX = dataBean.getGotoX();
                    if (RecommendMultiItem.isWeNeed(gotoX)) {
                        RecommendMultiItem item = new RecommendMultiItem();
                        item.setItemTypeWithGoto(gotoX, dataBean.getRcmd_reason() == null);
                        item.setIndexDataBean(dataBean);
                        if (RecommendMultiItem.isItemData(gotoX)) {
                            item.setOdd(isOdd);
                            isOdd = !isOdd;
                        } else {
                            isOdd = true;
                        }
                        list.add(item);
                    }
                }
            }
        }
        return list;
    }
}
