package com.pzj.vplibrary.http;

import com.pzj.vplibrary.dialog.VpProgressDialog;

import rx.Subscriber;

/**
 * Created by pzj on 2016/12/21.
 *
 * 用于统一处理请求开始,和请求结束操作(如显示和关闭加载框)
 *
 */

public class BaseSubscriber extends Subscriber {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(Object o) {

    }
}
