package com.noisyz.mvvmbase.ui.fragment.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by TESSARACT2 on 31.10.2017.
 */

public class TabsAdapterWithSaving extends TabsAdapterWithoutSaving {

    private Fragment[] fragments;

    public TabsAdapterWithSaving(FragmentManager fm, TabProvider tabProvider) {
        super(fm, tabProvider);
        fragments = new Fragment[tabProvider.getCount()];
        for (int index = 0; index < tabProvider.getCount(); index++) {
            fragments[index] = tabProvider.getTab(index);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }


}
