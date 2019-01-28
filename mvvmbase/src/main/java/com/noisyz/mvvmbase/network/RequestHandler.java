package com.noisyz.mvvmbase.network;

import io.reactivex.functions.Consumer;

/**
 * Created by TESSARACT2 on 06.11.2017.
 */

public class RequestHandler<T> implements Consumer<T> {
    private BaseRequestCallback<T> baseRequestCallback;

    public RequestHandler(BaseRequestCallback<T> baseRequestCallback) {
        this.baseRequestCallback = baseRequestCallback;
    }

    @Override
    public void accept(T t) throws Exception {
        baseRequestCallback.onRequestSuccess(t);
    }
}
