package com.noisyz.mvvmbase.ui.fragment.tabs;

import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by TESSARACT2 on 31.10.2017.
 */

public abstract class TabsContainerFragment extends TabsContainerWithoutSaveFragment {

    protected FragmentStatePagerAdapter getAdapter(TabProvider tabProvider) {
        return new TabsAdapterWithSaving(getChildFragmentManager(), tabProvider);
    }

}
