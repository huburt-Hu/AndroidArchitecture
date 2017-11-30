package com.huburt.architecture.http.download;

/**
 * Created by hubert
 * <p>
 * Created on 2017/7/20.
 */

public interface DownloadObserver extends DownloadProgressListener{

    void beforeDownload();

    void onError(Throwable e);

    void onComplete();
}
