package com.huburt.app.api;

import com.huburt.app.entity.IndexData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by hubert
 * <p>
 * Created on 2017/12/1.
 */

public interface HomeService {

    @Headers({"Domain-Name: recommend"})
    @GET("/x/feed/index?appkey=1d8b6e7d45233436&build=515000&mobi_app=android&network=wifi&open_event=cold&platform=android&style=2&ts=1508556998&sign=8215c7d01711e2f11e75830d856d32f5")
    Call<IndexData> getRecommendIndexData(@Query("idx") int idx, @Query("pull") String pull, @Query("login_event") int login_event);//login_event为1时加载banner

}
