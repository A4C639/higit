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

public class PricePagerAdp extends PagerAdapter {

    private int[] prices = {
        R.layout.price_top,R.layout.price_bottom,
                R.layout.price_shoes,R.layout.price_accessory};

    private List<View> viewList;
    private LayoutInflater inflater;

    public PricePagerAdp(Context context){
        this.inflater = LayoutInflater.from(context);
        viewList = new ArrayList<>();
        for(int layout : prices){//加载视图
            viewList.add(inflater.inflate(layout,null));
        }
    }


    @Override
    public int getCount() {
        return prices.length;
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
