package com.example.xonvi.washing2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.MyArticle;
import com.example.xonvi.washing2.Entity.Thing;
import com.example.xonvi.washing2.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xonvi on 2017/2/10.
 */

public class ArticleRVAdp extends RecyclerView.Adapter<ArticleRVAdp.ArticleVHolder> {

    private List<MyArticle> articles;
    public ArticleRVAdp(List<MyArticle> articles){

        this.articles =articles;
    }


    @Override
    public ArticleVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_article_list, parent, false);
        return new ArticleVHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleVHolder holder, int position) {



        MyArticle article = articles.get(position);
        holder.tv_time.setText(article.getTime());
        holder.tv_total.setText(article.getTotal());

        Type type = new TypeToken<List<Thing>>() {
        }.getType();
        Log.e("url",article.getThingList().replaceAll("\'","\""));
        List<Thing> thinglist = new Gson().fromJson(article.getThingList().replaceAll("\'","\""), type);
        holder.lv_thing_list.setAdapter(new ArticleThingListAdp(thinglist));
        setListViewHeightBasedOnChildren(holder.lv_thing_list);


    }



    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleVHolder extends RecyclerView.ViewHolder {
        TextView tv_time ;
        ListView lv_thing_list;
        TextView tv_total ;


        public ArticleVHolder(View itemView) {
            super(itemView);
             tv_time = (TextView)itemView.findViewById(R.id.tv_item_article_list_time);
             lv_thing_list=(ListView)itemView.findViewById(R.id.lv_frg_bilist_thing_list);
             tv_total = (TextView)itemView.findViewById(R.id.tv_item_article_list_total);

        }
    }



//    /**
//     * @param  listView
//     * 此方法是本次listview嵌套listview的核心方法：计算parentlistview item的高度。
//     * 如果不使用此方法，无论innerlistview有多少个item，则只会显示一个item。
//     **/
//    public void setListViewHeightBasedOnChildren(ListView listView) {
//        // 获取ListView对应的Adapter
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {        return;    }
//        int totalHeight = 0;
//        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
//            // listAdapter.getCount()返回数据项的数目
//            View listItem = listAdapter.getView(i, null, listView);
//            // 计算子项View 的宽高
//            listItem.measure(0, 0);
//            // 统计所有子项的总高度
//            totalHeight += listItem.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }

    public  void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter=listView.getAdapter();
        Log.e("count:::",listAdapter.getCount()+"  ");
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
}

