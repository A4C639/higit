package com.example.xonvi.washing2.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.DelArticleResEntity;
import com.example.xonvi.washing2.Entity.MyArticle;
import com.example.xonvi.washing2.Entity.Thing;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.aty.ActivityComment;
import com.example.xonvi.washing2.presenter.CommentPresenter;
import com.example.xonvi.washing2.presenter.DelArticlePresenter;
import com.example.xonvi.washing2.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/12.
 */

public class ArticleAdp extends BaseAdapter {

    private List<MyArticle> myArticleList;
    private Context context;
    private LayoutInflater inflater;
    public ArticleAdp(List<MyArticle> myArticles,Context context){
        this.myArticleList = myArticles;
        this.context  = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return myArticleList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_article_list,null);
        }

        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_item_article_list_time);
        ListView lv_thing_list = (ListView) convertView.findViewById(R.id.lv_frg_bilist_thing_list);
        TextView tv_total = (TextView) convertView.findViewById(R.id.tv_item_article_list_total);
        TextView tv_comment_article=(TextView) convertView.findViewById(R.id.tv_item_article_list_comment);
        TextView tv_del_article = (TextView) convertView.findViewById(R.id.tv_item_article_list_del);

        //将物品列表解析为数据集合
        String thingList = myArticleList.get(position).getThingList();
        String replaceAll = thingList.replaceAll("\'", "\"");

        try {
            //通过gson解析物品列表
            List<Thing> list = (List<Thing>) new Gson().fromJson(replaceAll,new TypeToken<List<Thing>>(){}.getType());
            lv_thing_list.setAdapter(new ArticleThingListAdp(list));
        } catch (Exception e) {e.printStackTrace();}

        //根据内层listview的item实际高度计算内层listview的高度
        setListViewHeightBasedOnChildren(lv_thing_list);
        tv_time.setText(myArticleList.get(position).getTime());
        tv_total.setText("合计 "+myArticleList.get(position).getTotal());
        tv_comment_article.setOnClickListener(new MyArticleItemClick(position));
        tv_del_article.setOnClickListener(new MyArticleItemClick(position));

        return convertView;
    }

    //根据内层listview的item实际高度计算内层listview的高度------------------------------------------
    public  void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }



    //订单列表中 评论按钮和删除按钮用到的监听器-------------------
    public class MyArticleItemClick implements View.OnClickListener {
        private int position;
        public MyArticleItemClick(int position){

            this.position = position;
        }
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                //评论订单
                case R.id.tv_item_article_list_comment:
                    context.startActivity(new Intent(context, ActivityComment.class));
                    break;
                //删除订单
                case R.id.tv_item_article_list_del:
                    new AlertDialog.Builder(context)
                            .setTitle("确定删除？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    delArticle();
                                }
                            })
                            .setNegativeButton("取消",null)
                            .show();
                    break;
            }

        }


        //删除订单
        private void delArticle() {
            //提交后台删除请求
            new DelArticlePresenter().doDelArticle(myArticleList.get(position).getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<DelArticleResEntity>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.toast(e.toString());
                            Log.e("delarticleexception",e.toString());
                        }
                        @Override
                        public void onNext(DelArticleResEntity entity) {
                            ToastUtil.toast(entity.getMsg());
                            if(entity.getCode()==1){
                                myArticleList.remove(position);
                                ArticleAdp.this.notifyDataSetChanged();
                            }
                        }
                    });

        }
    }

}
