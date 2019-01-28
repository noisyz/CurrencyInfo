package com.noisyz.mvvmbase.ui.fragment.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by TESSARACT2 on 31.10.2017.
 */

public class TabsAdapterWithoutSaving extends FragmentStatePagerAdapter {

    private TabProvider tabProvider;

    public TabsAdapterWithoutSaving(FragmentManager fm, TabProvider tabProvider) {
        super(fm);
        this.tabProvider = tabProvider;
    }

    @Override
    public Fragment getItem(int position) {
        return tabProvider.getTab(position);
    }

    @Override
    public String getPageTitle(int position) {
        return tabProvider.getTitle(position);
    }

    @Override
    public int getCount() {
        return tabProvider.getCount();
    }
}
