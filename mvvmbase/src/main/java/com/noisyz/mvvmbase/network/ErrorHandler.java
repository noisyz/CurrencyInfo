package com.noisyz.mvvmbase.network;

import io.reactivex.functions.Consumer;

/**
 * Created by TESSARACT2 on 06.11.2017.
 */

public class ErrorHandler implements Consumer<Throwable> {
    private static final int UNKNOWN_ERROR = -1;

    private BaseErrorCallback callback;

    public ErrorHandler(BaseErrorCallback errorCallback) {
        this.callback = errorCallback;
    }

    @Override
    public void accept(Throwable e) throws Exception {
        this.callback.onRequestError(UNKNOWN_ERROR, e.getLocalizedMessage());
        e.printStackTrace();
    }
}
