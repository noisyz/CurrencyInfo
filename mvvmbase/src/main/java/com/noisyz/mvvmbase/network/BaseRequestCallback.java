package com.noisyz.mvvmbase.network;

public interface BaseRequestCallback<T> extends BaseErrorCallback {
    void onRequestSuccess(T t);
}
