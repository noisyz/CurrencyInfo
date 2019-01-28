package com.noisyz.mvvmbase.ui.activity.abs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.noisyz.mvvmbase.R;


public abstract class FragmentContainerActivity extends AppCompatActivity {
    protected abstract Fragment initFragment();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        Fragment fragment = initFragment();
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
    }
}
