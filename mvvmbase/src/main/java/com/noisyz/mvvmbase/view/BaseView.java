package com.noisyz.mvvmbase.view;

import android.content.Context;

import com.noisyz.mvvmbase.viewmodel.BaseViewModel;

public interface BaseView {
    Context getContext();

    void hideProgressView();

    BaseViewModel initViewModel();

    void releasePresenter();

    void showError(int i, String str);

    void showProgressView();
}
