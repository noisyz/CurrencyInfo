package com.noisyz.mvvmbase.viewmodel.impl.single.abs;


import com.noisyz.mvvmbase.network.BaseRequestCallback;
import com.noisyz.mvvmbase.network.RequestInteractor;
import com.noisyz.mvvmbase.view.ItemView;
import com.noisyz.mvvmbase.viewmodel.impl.BaseViewModelImpl;

import io.reactivex.Single;


public abstract class BaseItemViewModelImpl<T> extends BaseViewModelImpl<ItemView<T>> implements BaseRequestCallback<T> {
    protected abstract Single<T> initRequest();

    public BaseItemViewModelImpl(ItemView<T> tItemView) {
        super(tItemView);
    }

    public void requestData() {
        if (!this.isStillLoading) {
            this.isStillLoading = true;
            if (getView() != null) {
                getView().showProgressView();
            }
            Single<T> request = initRequest();
            if (request != null) {
                new RequestInteractor().makeRequest(request, this);
            }
        }
    }

    public void onRequestSuccess(T t) {
        this.isStillLoading = false;
        if (getView() != null) {
            getView().onResult(t);
            getView().hideProgressView();
        }
    }
}
