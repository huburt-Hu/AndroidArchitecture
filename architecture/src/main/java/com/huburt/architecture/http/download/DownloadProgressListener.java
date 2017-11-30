package com.huburt.architecture.http.download;

public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}