package com.example.xonvi.washing2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.xonvi.washing2.Entity.CartListEvent;
import com.example.xonvi.washing2.Entity.Thing;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.TopRVAdapter;
import com.example.xonvi.washing2.presenter.ThingsListPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/5.
 */

public class FragmentShoes extends Fragment implements TopRVAdapter.MyClickListener{
    //recyclerview
    @BindView(R.id.rv_frg_shoes)RecyclerView rv_frg_shoes;
    @BindView(R.id.fl_loading_anima_shoes)FrameLayout fl_loading_anima_shoes;

    private ThingsListPresenter listPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_shoes, null);
        listPresenter = new ThingsListPresenter();
        ButterKnife.bind(this,view);

        initShoesList();
        return view;
    }

    //初始化上衣列表
    private void initShoesList()  {
        fl_loading_anima_shoes.setVisibility(View.VISIBLE);

        listPresenter.getShoesList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<Thing>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("shoes error",e.toString());
                        fl_loading_anima_shoes.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(List<Thing> topResEntity) {

                        Log.e("谢靴--",topResEntity.size()+"-----------------");
                        if(topResEntity.size()>0){
                            //适配物品列表
                            adp(topResEntity);
                        }
                        fl_loading_anima_shoes.setVisibility(View.GONE);
                    }
                });
    }

    //适配物品列表
    private void adp(List<Thing> list) {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_frg_shoes.setLayoutManager(staggeredGridLayoutManager);
        TopRVAdapter adapter = new TopRVAdapter(list);
        //初始化回掉接口
        adapter.setMyClickListener(this);
        rv_frg_shoes.setAdapter(adapter);
    }

    //回掉点击事件
    @Override
    public void myClick(View view, int pos,List<Thing> thingList) {
        Log.e("pos",pos+"  "+thingList.get(pos).getName());
        //将回掉出来的数据通过eventbus发送到宿主activity
        EventBus.getDefault().post(new CartListEvent(CartListEvent.ADD_TO_CART,thingList.get(pos),(ImageView) view));
    }
}
