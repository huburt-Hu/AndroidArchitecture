package com.huburt.app.mvvm.m;

import com.huburt.app.api.Api;
import com.huburt.app.api.VideoDetailService;
import com.huburt.app.entity.PlayUrl;
import com.huburt.architecture.http.RetrofitFactory;
import com.huburt.architecture.mvvm.ActionLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/6.
 */

public class VideoModel {

    private ActionLiveData<PlayUrl> playUrlLiveData = new ActionLiveData<>();

    public ActionLiveData<PlayUrl> getVideo(String aid) {
        VideoDetailService service = RetrofitFactory.getDefaultRetrofit(Api.RECOMMEND_BASE_URL, null)
                .create(VideoDetailService.class);
        service.getPlayurl(aid).enqueue(new Callback<PlayUrl>() {
            @Override
            public void onResponse(Call<PlayUrl> call, Response<PlayUrl> response) {
                Timber.i("getVideo succeed");
                playUrlLiveData.setValue(response.body());
                playUrlLiveData.setAction(2,"success");
            }

            @Override
            public void onFailure(Call<PlayUrl> call, Throwable t) {
                Timber.i("getVideo failed");
                playUrlLiveData.setValue(null);
                playUrlLiveData.setAction(1);
            }
        });
        return playUrlLiveData;
    }
}
