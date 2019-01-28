package com.noisyz.mvvmbase.ui.single.abs;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.noisyz.mvvmbase.R;
import com.noisyz.mvvmbase.view.ItemView;
import com.noisyz.mvvmbase.viewmodel.impl.single.abs.BaseItemViewModelImpl;

public abstract class RequestItemFragment<T> extends Fragment implements ItemView<T> {
    private BaseItemViewModelImpl<T> baseItemViewModel;

    protected abstract int getContentLayoutId();

    public abstract BaseItemViewModelImpl<T> initViewModel();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.baseItemViewModel = initViewModel();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ViewGroup contentContainer = (ViewGroup) view.findViewById(R.id.content_container);
        if (contentContainer != null) {
            contentContainer.addView(inflater.inflate(getContentLayoutId(), null));
        }
        return view;
    }

    protected int getLayoutId() {
        return R.layout.fragment_item;
    }

    public void showProgressView() {
        changeProgressVisibility(View.VISIBLE);
        changeContentVisibility(View.GONE);
    }

    public void hideProgressView() {
        changeProgressVisibility(View.GONE);
        changeContentVisibility(View.VISIBLE);
    }

    protected void changeProgressVisibility(int visibility) {
        if (getView() != null) {
            View progressView = getView().findViewById(R.id.progress);
            if (progressView != null) {
                progressView.setVisibility(visibility);
            }
        }
    }

    protected void changeContentVisibility(int visibility) {
        if (getView() != null) {
            View progressView = getView().findViewById(R.id.content_container);
            if (progressView != null) {
                progressView.setVisibility(visibility);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        releaseViewModel();
    }

    public void releaseViewModel() {
        this.baseItemViewModel.release();
        this.baseItemViewModel = null;
    }

    public void showError(int errorCode, String message) {
        if (getView() != null) {
            Snackbar.make(getView(), message, Toast.LENGTH_SHORT).show();
        }
    }

    public Context getContext() {
        return getActivity();
    }

    public void requestData() {
        this.baseItemViewModel.requestData();
    }

    public BaseItemViewModelImpl<T> getViewModel() {
        return this.baseItemViewModel;
    }
}
