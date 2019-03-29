package com.altsoft.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.altsoft.loggalapp.Fragement.TabFragment_Banner;
import com.altsoft.loggalapp.Fragement.TabFragment_localbox;
import com.altsoft.loggalapp.Fragement.TabFragment3;


public class TabPagerAdapter extends FragmentStatePagerAdapter {
    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                TabFragment_Banner tabFragment1 = new TabFragment_Banner();
                return tabFragment1;
            case 1:
                TabFragment_localbox tabFragment2 = new TabFragment_localbox();
                return tabFragment2;
            case 2:
                TabFragment3 tabFragment3 = new TabFragment3();
                return tabFragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
