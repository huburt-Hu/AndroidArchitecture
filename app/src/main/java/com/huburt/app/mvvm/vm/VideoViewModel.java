package com.huburt.app.mvvm.vm;

import android.arch.core.util.Function;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.huburt.app.entity.PlayUrl;
import com.huburt.app.mvvm.m.VideoModel;
import com.huburt.architecture.mvvm.ActionLiveData;
import com.huburt.architecture.mvvm.ActionTransformations;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/6.
 */

public class VideoViewModel extends ViewModel {

    private VideoModel videoModel = new VideoModel();
    private MutableLiveData<String> param = new MutableLiveData<>();
    private ActionLiveData<PlayUrl> playUrlLiveData;

    public VideoViewModel() {
        playUrlLiveData = ActionTransformations.switchMap(param, new Function<String, ActionLiveData<PlayUrl>>() {
            @Override
            public ActionLiveData<PlayUrl> apply(String input) {
                return videoModel.getVideo(input);
            }
        });
    }

    public ActionLiveData<PlayUrl> getVideoLiveData() {
        return playUrlLiveData;
    }

    public void getVideo(String aid) {
        param.setValue(aid);
    }
}
