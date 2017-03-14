package com.example.xonvi.washing2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.Thing;

import com.example.xonvi.washing2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xonvi on 2017/2/10.
 */

//订单列表上 物品列表的适配器
public class ArticleThingListAdp extends BaseAdapter {

    private List<Thing> thingList;
    public ArticleThingListAdp(List<Thing> list){
        this.thingList = list;
    }

    @Override
    public int getCount() {
        return thingList.size();
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


        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            convertView = inflater.inflate(R.layout.item_article_list_thing_list,null);
        }

        SimpleDraweeView sdv_thing_icon = (SimpleDraweeView) convertView.findViewById(R.id.sdv_item_article_list_thing_list_icon);
        TextView tv_thing_name = (TextView) convertView.findViewById(R.id.tv_item_article_list_thing_list_name);
        TextView tv_thing_price = (TextView) convertView.findViewById(R.id.tv_item_article_list_thing_list_price);
        TextView tv_thing_count = (TextView) convertView.findViewById(R.id.tv_item_article_list_thing_list_count);

        Thing thing = thingList.get(position);


        //里面的 “=” 全部变成了 u003 通过字符串操作更正即可
        String icon = thing.getIcon().replaceAll("u003d", "=");

        sdv_thing_icon.setImageURI(icon);
        tv_thing_count.setText("x"+thing.getCount());
        tv_thing_name.setText(thing.getName());
        tv_thing_price.setText(thing.getPrice()+"");


        return convertView;
    }





}
