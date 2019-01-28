package com.noisyz.mvvmbase.view;

public interface ItemView<T> extends BaseView {
    void onResult(T t);

    void requestData();
}
