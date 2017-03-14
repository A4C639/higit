package com.example.xonvi.washing2.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xonvi.washing2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xonvi on 2017/2/18.
 */

public class IntroPagerAdp extends PagerAdapter {

    private int[] intro = {
        R.layout.intro_view_order,R.layout.intro_view_get,
                R.layout.intro_view_wash,R.layout.intro_view_care,
                R.layout.intro_view_send,R.layout.intro_view_complete};

    private List<View> viewList;
    private LayoutInflater inflater;

    public IntroPagerAdp(Context context){
        this.inflater = LayoutInflater.from(context);
        viewList = new ArrayList<>();
        for(int layout : intro){//加载视图
            viewList.add(inflater.inflate(layout,null));
        }
    }


    @Override
    public int getCount() {
        return intro.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object==view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(viewList.get(position));

        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }
}
