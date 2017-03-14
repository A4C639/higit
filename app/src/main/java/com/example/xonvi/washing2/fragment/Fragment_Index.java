package com.example.xonvi.washing2.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.aty.ActivityCmtList;
import com.example.xonvi.washing2.aty.ActivityIntroduce;
import com.example.xonvi.washing2.aty.ActivityPrice;
import com.example.xonvi.washing2.aty.ActivityThings;
import com.example.xonvi.washing2.util.AnimatorUtils;
import com.example.xonvi.washing2.util.ThingUtil;
import com.example.xonvi.washing2.view.NetworkImageHolderView;
import com.ogaclejapan.arclayout.Arc;
import com.ogaclejapan.arclayout.ArcLayout;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xonvi on 2016/12/13.
 */

public class Fragment_Index extends Fragment implements OnItemClickListener {

    //banner
    @BindView(R.id.conv_banner)ConvenientBanner convenientBanner;
    private String url1 = "http://a4.topitme.com/l/201007/14/12790967632038.jpg";
    private String url2 = "http://a4.topitme.com/l/201008/31/12832150563280.jpg";
    private String url3 = "http://a4.topitme.com/l/201007/04/12782006429731.jpg";
    private List<String> stringList;

    @OnClick({R.id.tv_frg_index_accessory,R.id.tv_frg_index_bottom,R.id.tv_frg_index_top,R.id.tv_frg_index_shoes
    })
    public void click(View view){
        switch (view.getId()){
            case R.id.tv_frg_index_top:
                startActivity(new Intent(getActivity(),ActivityThings.class).putExtra("thing",ThingUtil.TOP));
                break;
            case R.id.tv_frg_index_bottom:
                startActivity(new Intent(getActivity(),ActivityThings.class).putExtra("thing",ThingUtil.BOTTOM));
                break;
            case R.id.tv_frg_index_shoes:
                startActivity(new Intent(getActivity(),ActivityThings.class).putExtra("thing",ThingUtil.SHOES));
                break;
            case R.id.tv_frg_index_accessory:
                startActivity(new Intent(getActivity(),ActivityThings.class).putExtra("thing",ThingUtil.ACCESSORY));
                break;
//            //服务介绍
//            case R.id.tv_frg_index_introduce:
//                startActivity(new Intent(getActivity(), ActivityIntroduce.class));
//                break;
//            //价目中心
//            case R.id.tv_frg_index_pricecenter:
//                startActivity(new Intent(getActivity(), ActivityPrice.class));
//                break;
//            //用户评价
//            case R.id.tv_frg_index_comment:
//                startActivity(new Intent(getActivity(), ActivityCmtList.class));
//                break;
            //浮动按钮

        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_index,null);
        //butterknife绑定fragment
        ButterKnife.bind(this,view);
        initBanner();
        return view;
    }



    // 初始化 banner需要的数据
    public void initBanner(){
        stringList = new ArrayList<>();
        stringList.add(url1);
        stringList.add(url2);
        stringList.add(url3);

        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },stringList).setOnItemClickListener(this)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT) //设置指示器位置（左、中、右）
                .startTurning(3000)     //设置自动切换（同时设置了切换时间间隔）
                .setPageIndicator(new int[]{ R.mipmap.ic_fiber_manual_record_pressed,R.mipmap.ic_fiber_manual_record_normal})   //设置指示器圆点
                .setPointViewVisible(true)    //设置指示器是否可见
                .setManualPageable(true);  //设置手动影响（设置了该项无法手动切换）
//                .stopTurning()    //关闭自动切换
    }
    @Override
    public void onStop() {
        super.onStop();

    }

    //banner的item click=--------------------------------------------------------------------------------------
    @Override
    public void onItemClick(int position) {
        Log.e("pos",position+" ");
    }




}
