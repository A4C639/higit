package com.example.xonvi.washing2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by xonvi on 2017/2/5.
 */

public class TingsFrgPagerAdp extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    public TingsFrgPagerAdp(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
