package com.pzj.vplibrary.http;


/**
 * Created by pzj on 2016/12/15.
 * des:上传,下载进度监听
 */

public interface ProgressListener {
    void onProgress(long total,long progress);
    void onFinished(String path);
    void onFailed(Exception e);
}
