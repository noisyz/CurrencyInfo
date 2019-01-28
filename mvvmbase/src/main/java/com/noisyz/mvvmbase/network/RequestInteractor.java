package com.noisyz.mvvmbase.network;


import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RequestInteractor<T> {
    public void makeRequest(Single<T> observable, BaseRequestCallback<T> callback) {
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new RequestHandler<T>(callback), new ErrorHandler(callback));
    }
}
