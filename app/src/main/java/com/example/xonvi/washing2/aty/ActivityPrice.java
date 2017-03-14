package com.example.xonvi.washing2.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.PricePagerAdp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xonvi on 2017/2/18.
 */

//价目列表
public class ActivityPrice extends BaseActivity implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    //tablayout
    @BindView(R.id.tab_aty_price)TabLayout tab_aty_price;
    //viewpager
    @BindView(R.id.vp_aty_price)ViewPager vp_aty_price;

    private List<TabLayout.Tab> tabList;

    @OnClick({R.id.iv_aty_new_price_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_new_price_back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        ButterKnife.bind(this);


        initPriceCenter();
    }

    private void initPriceCenter() {
        //初始化tablayout
        initTabs();
        initViewPager();

    }

    //初始化viewpager------------------------------------------------------
    private void initViewPager() {
        vp_aty_price.setAdapter(new PricePagerAdp(this));
        vp_aty_price.setOnPageChangeListener(this);

    }

    //初始化tablauout======---------------------------------------------
    private void initTabs() {
        tabList = new ArrayList<>();
        tabList.add(tab_aty_price.newTab().setText("衣服"));
        tabList.add(tab_aty_price.newTab().setText("裤裙"));
        tabList.add(tab_aty_price.newTab().setText("鞋靴"));
        tabList.add(tab_aty_price.newTab().setText("配饰"));
        for (int i = 0;i<tabList.size();i++){
            tab_aty_price.addTab(tabList.get(i));
        }
        tab_aty_price.setOnTabSelectedListener(this);
    }



    //viewpager选中页面的监听器----------------------------------------------------------
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabList.get(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //tab被选中的时的监听器-----------------------------------------------------------------
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getText().equals(tabList.get(0))){
            vp_aty_price.setCurrentItem(0);

        }else if(tab.getText().equals(tabList.get(1))){
            vp_aty_price.setCurrentItem(1);
        }else if(tab.getText().equals(tabList.get(2))){
            vp_aty_price.setCurrentItem(2);
        }else if(tab.getText().equals(tabList.get(3))){
            vp_aty_price.setCurrentItem(3);
        }


    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
