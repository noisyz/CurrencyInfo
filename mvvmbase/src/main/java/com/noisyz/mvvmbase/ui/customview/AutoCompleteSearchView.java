package com.noisyz.mvvmbase.ui.customview;

import android.content.Context;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.noisyz.mvvmbase.R;


/**
 * Created by nero232 on 19.06.17.
 */

public class AutoCompleteSearchView extends SearchView {

    private SearchAutoComplete mSearchAutoComplete;

    public AutoCompleteSearchView(Context context) {
        super(context);
        initialize();
    }

    public AutoCompleteSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void initialize() {
        mSearchAutoComplete = (SearchAutoComplete) findViewById(android.support.v7.appcompat.R.id.search_src_text);
        mSearchAutoComplete.setDropDownBackgroundResource(R.drawable.dropdown_menu_selector);
        this.setAdapter(null);
        this.setOnItemClickListener(null);
    }

    @Override
    public void setSuggestionsAdapter(CursorAdapter adapter) {
        // don't let anyone touch this
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mSearchAutoComplete.setOnItemClickListener(listener);
    }

    public void setAdapter(ArrayAdapter<?> adapter) {
        mSearchAutoComplete.setAdapter(adapter);
    }

    public void setText(String text) {
        mSearchAutoComplete.setText(text);
    }

}
