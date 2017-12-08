package com.huburt.app.mvvm.vm;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.huburt.app.entity.RecommendMultiItem;
import com.huburt.app.mvvm.m.RecommendModel;
import com.huburt.architecture.mvvm.ActionLiveData;
import com.huburt.architecture.mvvm.ActionTransformations;

import java.util.List;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/1.
 */

public class RecommendViewModel extends ViewModel {

    private RecommendModel model = new RecommendModel();

    private MutableLiveData<Params> controller = new MutableLiveData<>();
    private ActionLiveData<List<RecommendMultiItem>> recommendData;

    public RecommendViewModel() {
        recommendData = ActionTransformations.switchMap(controller, new Function<Params, ActionLiveData<List<RecommendMultiItem>>>() {
            @Override
            public ActionLiveData<List<RecommendMultiItem>> apply(Params input) {
                return model.getRecommendData(input.idx, input.pull, input.login_event);
            }
        });
    }

    public ActionLiveData<List<RecommendMultiItem>> getRecommendLiveData() {
        return recommendData;
    }

    public void updatePage(int idx, boolean refresh, boolean clearCache) {
        controller.setValue(new Params(idx, refresh ? "true" : "false", clearCache ? 1 : 0));
    }

    public class Params {
        int idx;
        String pull;
        int login_event;

        Params(int idx, String pull, int login_event) {
            this.idx = idx;
            this.pull = pull;
            this.login_event = login_event;
        }
    }

}
