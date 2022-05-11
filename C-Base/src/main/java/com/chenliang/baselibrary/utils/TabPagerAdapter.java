package com.chenliang.baselibrary.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private Fragment[] tabFragments;

    public TabPagerAdapter(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public TabPagerAdapter(@NonNull FragmentManager fragmentManager, Fragment[] tabFragments) {
        super(fragmentManager);
        this.tabFragments = tabFragments;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return tabFragments[position];
    }

    @Override
    public int getCount() {
        return this.tabFragments.length;
    }
}
