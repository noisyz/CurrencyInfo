package com.noisyz.mvvmbase.ui.single.abs;

import android.os.Bundle;
import android.view.View;

public abstract class ShowItemFragment<T> extends RequestItemFragment<T> {
    protected abstract void showItem(T t);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onResume() {
        super.onResume();
        requestData();
    }

    protected void showEmptyView(View view) {
    }

    protected void hideEmptyView(View view) {
    }

    public void onResult(T t) {
        if (t != null) {
            hideEmptyView(getView());
            showItem(t);
            return;
        }
        showEmptyView(getView());
    }
}
