package com.huburt.architecture.http.download;

import android.util.Log;

import com.huburt.architecture.http.RetrofitFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/20.
 * </p>
 * <p>基于Retrofit+OKHttp实现的建议下载</p>
 */

public class DownloadManager {

    private static final String DEFAULT_BASE_URL = "";

    private DownloadObserver downloadObserver;
    private DownloadService downloadService;

    public DownloadManager(DownloadObserver observer) {
        this(DEFAULT_BASE_URL, observer);
    }

    public DownloadManager(String baseUrl, DownloadObserver observer) {
        downloadObserver = observer;
        OkHttpClient client = RetrofitFactory.getDefaultOKHttpClientBuilder(null)
                .addInterceptor(chain -> {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), downloadObserver))
                            .build();
                })
                .build();

        downloadService = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DownloadService.class);
    }

    public void download(String url, final File file) {
        downloadService.download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (downloadObserver != null)
                        downloadObserver.beforeDownload();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(responseBody -> responseBody.byteStream())
                .observeOn(Schedulers.computation())
                .doOnNext(inputStream -> saveFile(inputStream, file))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InputStream>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(InputStream inputStream) {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (downloadObserver != null) {
                            downloadObserver.onError(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (downloadObserver != null) {
                            downloadObserver.onComplete();
                        }
                    }
                });
    }

    private void saveFile(InputStream is, File file) {
        String path = file.getParent();
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            if (downloadObserver != null) {
                downloadObserver.onError(e);
            }
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Log.e("saveFile", e.getMessage());
            }
        }
    }
}
