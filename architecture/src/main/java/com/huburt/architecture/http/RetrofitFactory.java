package com.huburt.architecture.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hubert
 * <p>
 * Created on 2017/11/29.
 */

public final class RetrofitFactory {

    private static Map<String, Retrofit> retrofits = new HashMap<>();

    private RetrofitFactory() {
    }

    public static OkHttpClient.Builder getOKHttpClientBuilder() {
        return new OkHttpClient.Builder();
    }

    public static OkHttpClient.Builder getDefaultOKHttpClientBuilder(@Nullable GlobalHttpHandler handler) {
        return getOKHttpClientBuilder()
                .addInterceptor(new RequestInterceptor(handler, RequestInterceptor.Level.ALL))
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
    }

    public static Retrofit.Builder getRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @NonNull
    private static Retrofit.Builder getDefaultRetrofitBuilder(String baseUrl, @Nullable GlobalHttpHandler handler) {
        return getRetrofitBuilder()
                .baseUrl(baseUrl)
                .client(getDefaultOKHttpClientBuilder(handler).build())
                .addConverterFactory(GsonConverterFactory.create());
    }

    public static Retrofit getDefaultRetrofit(String baseUrl, @Nullable GlobalHttpHandler handler) {
        Retrofit retrofit = retrofits.get(baseUrl);
        if (retrofit == null) {
            retrofit = getDefaultRetrofitBuilder(baseUrl, handler)
                    .build();
            retrofits.put(baseUrl, retrofit);
        }
        return retrofit;
    }
}
