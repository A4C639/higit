package com.example.xonvi.washing2.aty;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.AddrListResEntity;
import com.example.xonvi.washing2.Entity.MyAddr;
import com.example.xonvi.washing2.Entity.MyEvent;
import com.example.xonvi.washing2.Entity.UpEvent;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.AddrListAdp;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.fragment.Fragment_Identity;
import com.example.xonvi.washing2.presenter.AddrPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



/**
 * Created by xonvi on 2017/2/8.
 */

//管理收货地址的activity
public class ActivityAddress extends BaseActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    //打开地址页面大的模式
    private int pattern;
    //地址列表集合
    private List<MyAddr> addrList;
    //刷新swiprefresh
    @BindView(R.id.refresh_aty_address)SwipeRefreshLayout refresh_aty_address;
    //显示地址的listview
    @BindView(R.id.lv_aty_address)ListView lv_aty_address;
    @BindView(R.id.tv_aty_noaddress)TextView tv_aty_noaddress;
    @OnClick({R.id.iv_aty_address_back,R.id.tv_aty_address_manage})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_address_back:
                this.finish();
                break;
            //进入修改地址列表的页面
            case R.id.tv_aty_address_manage:
                openActivity(ActivityEditAddr.class);
                break;
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        //eventbus
        EventBus.getDefault().register(this);
        //初始化地址列表
        initAddressList();
        //设置下拉刷新
        initSwipeRefresh();
        //获取打开模式
        initPattern();
    }

    //获取浏览模式
    private void initPattern() {
        pattern = getIntent().getIntExtra("PATTERN",0);
    }


    //设置下拉刷新
    private void initSwipeRefresh() {
        refresh_aty_address.setOnRefreshListener(this);
        refresh_aty_address.setColorSchemeColors(Color.parseColor("#339999"));
    }

    private void initAddressList() {

        //开始刷新
        refresh_aty_address.setRefreshing(true);
        lv_aty_address.setOnItemClickListener(this);
        new AddrPresenter().getAddrList(MyApplication.getInstance().myUser.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<AddrListResEntity>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("addreatyaddress",e.toString());
                        refresh_aty_address.setRefreshing(false);
                    }
                    @Override
                    public void onNext(AddrListResEntity entity) {
                        if(entity.getCode()==1){//已经填写地址
                            //将地址列表保存到集合
                            addrList = entity.getData();
                            //显示订单列表
                            lv_aty_address.setAdapter(new AddrListAdp(entity.getData()));
                            lv_aty_address.setVisibility(View.VISIBLE);
                            tv_aty_noaddress.setVisibility(View.GONE);


                        }else {//没有填写地址
                            tv_aty_noaddress.setVisibility(View.VISIBLE);
                            lv_aty_address.setVisibility(View.GONE);
                        }
                        refresh_aty_address.setRefreshing(false);
                    }
                });
    }


    //地址列表item的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(pattern== Fragment_Identity.ADDRESS_SCAN){
            return;
        }
            setResult(RESULT_OK,new Intent().putExtra("address",addrList.get(position)));
            ActivityAddress.this.finish();

    }

    //swiperefresh----------------------------------------------------------------------------
    @Override
    public void onRefresh() {
        initAddressList();
    }
    //eventbus事件总线方法
    //这里主要接受来自ActivityEidtAddr页面发送过来的事件
    @Subscribe
    public void onEventMainThread(UpEvent event){
        //刷新地址列表
        initAddressList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
