package com.example.xonvi.washing2.aty;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.IntroPagerAdp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xonvi on 2017/2/18.
 */

//服务介绍
public class ActivityIntroduce extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.tv_step_order)TextView tv_step_order;
    @BindView(R.id.tv_step_get)TextView tv_step_get;
    @BindView(R.id.tv_step_wash)TextView tv_step_wash;
    @BindView(R.id.tv_step_care)TextView tv_step_care;
    @BindView(R.id.tv_step_send)TextView tv_step_send;
    @BindView(R.id.tv_step_complete)TextView tv_step_complete;

    @BindView(R.id.dot_order)TextView dot_order;
    @BindView(R.id.dot_get)TextView dot_get;
    @BindView(R.id.dot_wash)TextView dot_wash;
    @BindView(R.id.dot_care)TextView dot_care;
    @BindView(R.id.dot_send)TextView dot_send;
    @BindView(R.id.dot_complete)TextView dot_complete;

    @BindView(R.id.vp_aty_introduce)ViewPager vp_aty_introduce;
    @OnClick({R.id.iv_aty_introduce_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_introduce_back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        ButterKnife.bind(this);

        initIntroView();
    }

    //初始化介视图
    private void initIntroView() {
        vp_aty_introduce.setAdapter(new IntroPagerAdp(this));
        vp_aty_introduce.setOnPageChangeListener(this);
    }

    //viewpager----------------------------------------------------------------
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position){
            case 0:
                tv_step_order.setVisibility(View.VISIBLE);
                tv_step_get.setVisibility(View.INVISIBLE);
                tv_step_wash.setVisibility(View.INVISIBLE);
                tv_step_care.setVisibility(View.INVISIBLE);
                tv_step_send.setVisibility(View.INVISIBLE);
                tv_step_complete.setVisibility(View.INVISIBLE);

                break;
            case 1:
                tv_step_order.setVisibility(View.INVISIBLE);
                tv_step_get.setVisibility(View.VISIBLE);
                tv_step_wash.setVisibility(View.INVISIBLE);
                tv_step_care.setVisibility(View.INVISIBLE);
                tv_step_send.setVisibility(View.INVISIBLE);
                tv_step_complete.setVisibility(View.INVISIBLE);


                break;
            case 2:
                tv_step_order.setVisibility(View.INVISIBLE);
                tv_step_get.setVisibility(View.INVISIBLE);
                tv_step_wash.setVisibility(View.VISIBLE);
                tv_step_care.setVisibility(View.INVISIBLE);
                tv_step_send.setVisibility(View.INVISIBLE);
                tv_step_complete.setVisibility(View.INVISIBLE);


                break;
            case 3:
                tv_step_order.setVisibility(View.INVISIBLE);
                tv_step_get.setVisibility(View.INVISIBLE);
                tv_step_wash.setVisibility(View.INVISIBLE);
                tv_step_care.setVisibility(View.VISIBLE);
                tv_step_send.setVisibility(View.INVISIBLE);
                tv_step_complete.setVisibility(View.INVISIBLE);



                break;
            case 4:
                tv_step_order.setVisibility(View.INVISIBLE);
                tv_step_get.setVisibility(View.INVISIBLE);
                tv_step_wash.setVisibility(View.INVISIBLE);
                tv_step_care.setVisibility(View.INVISIBLE);
                tv_step_send.setVisibility(View.VISIBLE);
                tv_step_complete.setVisibility(View.INVISIBLE);


                break;
            case 5:
                tv_step_order.setVisibility(View.INVISIBLE);
                tv_step_get.setVisibility(View.INVISIBLE);
                tv_step_wash.setVisibility(View.INVISIBLE);
                tv_step_care.setVisibility(View.INVISIBLE);
                tv_step_send.setVisibility(View.INVISIBLE);
                tv_step_complete.setVisibility(View.VISIBLE);

                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
