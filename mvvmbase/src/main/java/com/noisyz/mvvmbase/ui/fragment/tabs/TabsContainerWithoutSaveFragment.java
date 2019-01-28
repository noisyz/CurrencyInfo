package com.noisyz.mvvmbase.ui.fragment.tabs;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noisyz.mvvmbase.R;


/**
 * Created by TESSARACT2 on 31.10.2017.
 */

public abstract class TabsContainerWithoutSaveFragment extends Fragment implements TabProvider {

    private String[] titles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout_fragment, null);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        try {
            titles = getActivity().getResources().getStringArray(getTitlesArrayResourceId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewPager.setAdapter(getAdapter(this));
        return view;
    }

    protected FragmentStatePagerAdapter getAdapter(TabProvider tabProvider) {
        return new TabsAdapterWithoutSaving(getChildFragmentManager(), tabProvider);
    }

    protected abstract int getTitlesArrayResourceId();

    public String getTitle(int index) {
        try {
            return titles[index];
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
