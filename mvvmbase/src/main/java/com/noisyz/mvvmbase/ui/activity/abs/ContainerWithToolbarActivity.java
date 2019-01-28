package com.noisyz.mvvmbase.ui.activity.abs;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;


/**
 * Created by nero232 on 29.07.17.
 */

public abstract class ContainerWithToolbarActivity extends FragmentContainerActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpToolbar() {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        String title = getActivityTitle();
        if (title != null) {
            setTitle(title);
        }
    }

    protected String getActivityTitle(){
        return null;
    }
}
