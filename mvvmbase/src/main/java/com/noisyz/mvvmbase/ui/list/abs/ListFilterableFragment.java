package com.noisyz.mvvmbase.ui.list.abs;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.noisyz.bindlibrary.base.impl.adapter.RecyclerFilterableBindAdapter;
import com.noisyz.bindlibrary.callback.filter.QueryItemCallback;
import com.noisyz.mvvmbase.R;

import java.util.List;

public abstract class ListFilterableFragment<T> extends ListItemFragment<T> implements QueryItemCallback<T>, OnQueryTextListener {
    protected RecyclerFilterableBindAdapter<T, String> adapter;
    private String query;

    protected abstract RecyclerFilterableBindAdapter<T, String> initFilterableItemsAdapter(List<T> list);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.inflateMenu(R.menu.menu_search);
            ((SearchView) MenuItemCompat.getActionView(toolbar.getMenu().findItem(R.id.action_search))).setOnQueryTextListener(this);
        }
    }

    protected RecyclerFilterableBindAdapter<T, String> initItemsAdapter(List<T> items) {
        this.adapter = initFilterableItemsAdapter(items);
        return this.adapter;
    }

    protected int getContentLayoutId() {
        return R.layout.fragment_list_with_toolbar;
    }

    public void showItem(List<T> items) {
        super.showItem(items);
        if (!TextUtils.isEmpty(this.query) && this.adapter != null) {
            this.adapter.filter(this.query);
        }
    }

    public boolean onQueryTextSubmit(String query) {
        query(query);
        return true;
    }

    public boolean onQueryTextChange(String newText) {
        query(newText);
        return true;
    }

    protected void query(String query) {
        this.query = query;
        if (query.isEmpty()) {
            this.adapter.restore();
        } else {
            this.adapter.filter(query);
        }
    }
}
