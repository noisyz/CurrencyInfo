package com.noisyz.mvvmbase.viewmodel.impl;

import com.noisyz.mvvmbase.network.BaseErrorCallback;
import com.noisyz.mvvmbase.view.BaseView;
import com.noisyz.mvvmbase.viewmodel.BaseViewModel;

public class BaseViewModelImpl<T extends BaseView> implements BaseViewModel, BaseErrorCallback {
    protected boolean isStillLoading;
    private T t;

    public BaseViewModelImpl(T t) {
        this.t = t;
    }

    protected T getView() {
        return this.t;
    }

    public void release() {
        this.t = null;
    }

    public void onRequestError(int errorCode, String errorMessage) {
        this.isStillLoading = false;
        if (this.t != null) {
            this.t.showError(errorCode, errorMessage);
            this.t.hideProgressView();
        }
    }
}
