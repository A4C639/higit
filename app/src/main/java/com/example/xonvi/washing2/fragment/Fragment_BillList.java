package com.example.xonvi.washing2.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.ArticleResEntity;
import com.example.xonvi.washing2.Entity.MyArticle;
import com.example.xonvi.washing2.Entity.MyEvent;
import com.example.xonvi.washing2.Entity.Thing;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.ArticleAdp;
import com.example.xonvi.washing2.adapter.ArticleRVAdp;
import com.example.xonvi.washing2.adapter.ArticleThingListAdp;
import com.example.xonvi.washing2.adapter.OrderRVAdp;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.aty.ActivityIndex;
import com.example.xonvi.washing2.aty.ActivityLogin;
import com.example.xonvi.washing2.presenter.ArticlePresenter;
import com.example.xonvi.washing2.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2016/12/13.
 */
//订单列表
public class Fragment_BillList extends Fragment implements ActivityIndex.ISendToFrgBilist, SwipeRefreshLayout.OnRefreshListener {
    private ArticlePresenter articlePresenter;


    @BindView(R.id.refresh_billist)SwipeRefreshLayout refresh_billist;
    //当没有登陆时账单页面的显示(有提示登陆按钮以及文字)
    @BindView(R.id.ll_frg_bilist_offline)LinearLayout ll_frg_bilist_offline;
    //用户登陆了但是没有订单信息的现实(仅仅显示文本提示)
    @BindView(R.id.tv_frg_bilist_noinfo)TextView tv_frg_bilist_noinfo;
    //账单列表
    @BindView(R.id.lv_frg_bilist)ListView lv_frg_bilist;

    @OnClick({R.id.tv_frg_billist_login})
    public void click(View view){
        switch (view.getId()){
            case R.id.tv_frg_billist_login:
                //跳转到登陆界面
                startActivity(new Intent(getActivity(), ActivityLogin.class));
                break;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_billlist,null);
        ButterKnife.bind(this,view);
        articlePresenter = new ArticlePresenter();

        //注册Activity的回掉接口 接受activity发送的事件
        ActivityIndex activityIndex = (ActivityIndex) getActivity();
        activityIndex.setiSendToFrgBilist(this);

        initBillList();
        return view;
    }

    //初始化订单列表
    private void initBillList(){
        //swiprefresh
        refresh_billist.setOnRefreshListener(this);
        refresh_billist.setColorSchemeColors(Color.parseColor("#339999"));
        refresh_billist.setVisibility(View.VISIBLE);
        refresh_billist.setRefreshing(true);

        if(MyApplication.getInstance().myUser==null){
            Log.e("用户状态","用户没有登录");
            ll_frg_bilist_offline.setVisibility(View.VISIBLE);
            tv_frg_bilist_noinfo.setVisibility(View.GONE);
            refresh_billist.setVisibility(View.GONE);
            return;

        }

        ToastUtil.toast(MyApplication.getInstance().myUser.getId()+"  ");
        //从网络获取订单列表的信息
        reqBillistInfo();

    }


    //从网络获取订单列表
    private void reqBillistInfo() {
        if(MyApplication.getInstance().myUser==null){
            return;
        }
        articlePresenter.getArticleList(MyApplication.getInstance().myUser.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ArticleResEntity>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("articlelistexception",e.toString());
                        ll_frg_bilist_offline.setVisibility(View.GONE);
                        tv_frg_bilist_noinfo.setVisibility(View.VISIBLE);
                        tv_frg_bilist_noinfo.setText("出错了");
                    }

                    @Override
                    public void onNext(ArticleResEntity entity) {
                        if(entity.getCode()==1){//成功返回订单

                            ll_frg_bilist_offline.setVisibility(View.GONE);
                        }else if(entity.getCode()==0){//返回订单失败
                            ll_frg_bilist_offline.setVisibility(View.GONE);
                            tv_frg_bilist_noinfo.setVisibility(View.VISIBLE);

                        }
                        //加载订单列表
                        loadArticleList(entity.getData());

                        //请求完成就停止刷新
                        if(refresh_billist.isRefreshing()){
                            refresh_billist.setRefreshing(false);
//                            ToastUtil.toast("刷新成功");
                        }
                    }
                });

    }

    //加载订单列表
    private void loadArticleList(List<MyArticle> myArticles) {
        lv_frg_bilist.setAdapter(new ArticleAdp(myArticles,getActivity()));
    }


    //当fragment用户可见的时候
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
//            Log.e("visiable",isVisibleToUser+"  ");
            reqBillistInfo();
        }
    }


    //ActivityIndex中发送消息的接口方法
    @Override
    public void msgGet(MyEvent event) {

        //刷新账单列表
        initBillList();
        Log.e("eventbillist","------刷新订单列表成功------");

    }

//swiprefresh---------------------------
    @Override
    public void onRefresh() {
        //从新加载账单列表
        initBillList();

    }
}
