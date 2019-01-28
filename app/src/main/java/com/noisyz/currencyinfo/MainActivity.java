package com.noisyz.currencyinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import androidx.navigation.fragment.NavHostFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.navFragment);
        if (fragment == null || !NavHostFragment.findNavController(fragment).navigateUp()) {
            super.onBackPressed();
        }
    }


}
