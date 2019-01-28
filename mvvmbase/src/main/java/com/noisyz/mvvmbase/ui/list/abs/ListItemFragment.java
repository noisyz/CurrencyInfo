package com.noisyz.mvvmbase.ui.list.abs;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noisyz.bindlibrary.base.impl.adapter.RecyclerBindAdapter;
import com.noisyz.bindlibrary.callback.clickevent.OnItemClickListener;
import com.noisyz.mvvmbase.R;
import com.noisyz.mvvmbase.ui.single.abs.ShowItemFragment;
import com.noisyz.mvvmbase.viewmodel.impl.single.abs.BaseItemViewModelImpl;

import java.util.List;

public abstract class ListItemFragment<T> extends ShowItemFragment<List<T>> implements OnRefreshListener, OnItemClickListener<T> {
    private RecyclerBindAdapter<T> adapter;
    private BaseItemViewModelImpl<List<T>> baseListViewModel;
    private List<T> items;
    private SwipeRefreshLayout swipeRefreshLayout;

    protected abstract RecyclerBindAdapter<T> initItemsAdapter(List<T> list);

    public abstract BaseItemViewModelImpl<List<T>> initViewModel();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.baseListViewModel = initViewModel();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = super.onCreateView(layoutInflater, parent, savedInstanceState);
        if (view != null) {
            RecyclerView recyclerView = view.findViewById(R.id.list);
            if (recyclerView != null) {
                initRecycler(recyclerView);
            }
            initSwipeRefreshLayout(view);
        }
        return view;
    }

    protected int getContentLayoutId() {
        return R.layout.fragment_list;
    }

    protected int getLayoutId() {
        return R.layout.fragment_list_view_item;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestData();
    }

    public void requestData() {
        this.baseListViewModel.requestData();
    }

    private void initSwipeRefreshLayout(View view) {
        this.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        if (this.swipeRefreshLayout != null) {
            this.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
            this.swipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    protected void initRecycler(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initAdapter(List<T> items, View view) {
        this.adapter = initItemsAdapter(items);
        this.adapter.setOnItemClickListener(this);
        ((RecyclerView) view.findViewById(R.id.list)).setAdapter(this.adapter);
    }

    public void showItem(List<T> items) {
        this.items = items;
        if (getView() != null) {
            initAdapter(items, getView());
            if (this.adapter.getItemCount() == 0) {
                showEmptyView(getView());
            } else {
                hideEmptyView(getView());
            }
        }
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
            if (this.swipeRefreshLayout != null) {
                this.swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    protected void changeContentVisibility(int visibility) {
        if (getView() != null) {
            View progressView = getView().findViewById(R.id.list);
            if (progressView != null) {
                progressView.setVisibility(visibility);
            }
        }
    }

    public void releasePresenter() {
        if (this.baseListViewModel != null) {
            this.baseListViewModel.release();
            this.baseListViewModel = null;
        }
    }

    public void showError(int errorCode, String message) {
        if (getView() != null) {
            Snackbar.make(getView(), message, -1).show();
        }
    }

    public void onRefresh() {
        this.swipeRefreshLayout.setRefreshing(true);
        if (this.baseListViewModel != null) {
            this.baseListViewModel.requestData();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        releasePresenter();
    }

    public BaseItemViewModelImpl<List<T>> getViewModel() {
        return this.baseListViewModel;
    }

    protected List<T> getItems() {
        return this.items;
    }

    public RecyclerBindAdapter<T> getAdapter() {
        return this.adapter;
    }

    public Context getContext() {
        return getActivity();
    }
}
