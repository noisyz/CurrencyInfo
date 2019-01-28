package com.noisyz.mvvmbase.ui.fragment.tabs;


import android.support.v4.app.Fragment;

/**
 * Created by TESSARACT2 on 26.11.2017.
 */

public interface TabProvider {
    Fragment getTab(int index);

    String getTitle(int index);

    int getCount();
}
