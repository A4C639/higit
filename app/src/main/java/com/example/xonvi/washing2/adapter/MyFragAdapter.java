package com.example.xonvi.washing2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xonvi.washing2.R;

import java.util.List;

/**
 * Created by xonvi on 2016/12/13.
 */

public class MyFragAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    //构造方法中传入一个Fragment的集合(就是我们想要显示的那几个fragment页面)
    public MyFragAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragments=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }

}
