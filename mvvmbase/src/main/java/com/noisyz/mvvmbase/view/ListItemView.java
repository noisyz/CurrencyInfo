package com.noisyz.mvvmbase.view;

import com.noisyz.bindlibrary.callback.clickevent.OnItemClickListener;

import java.util.List;

public interface ListItemView<T> extends BaseView, OnItemClickListener<T> {
    void onItemListLoaded(List<T> list);

    void requestData();
}
