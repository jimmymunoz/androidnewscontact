package com.example.jimmymunoz.testappspanel;

/**
 * Created by jimmymunoz on 12/8/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabNews tab1 = new TabNews();
                return tab1;
            case 1:
                TabInfo tab2 = new TabInfo();
                return tab2;
            case 2:
                TabContact tab3 = new TabContact();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}