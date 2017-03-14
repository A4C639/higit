package com.example.xonvi.washing2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xonvi.washing2.Entity.Thing;
import com.example.xonvi.washing2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by xonvi on 2017/2/6.
 */

//继承的Adapter必须是 自己创建的adapterviewholder的泛型
public class TopRVAdapter extends RecyclerView.Adapter<TopRVAdapter.TopVHolder>{

    private  Context context;
    //先定义自己的点击事件回掉接口 并将其作为adapter的属性
    private MyClickListener myClickListener;
    public interface MyClickListener{
        void myClick(View view,int pos,List<Thing> thingList);
    }
    //创建set方法在adapter外可以将它初始化
    public void setMyClickListener(MyClickListener myClickListener){
        this.myClickListener = myClickListener;
    }

    private List<Thing> things;
    public TopRVAdapter(List<Thing> things){
        this.things = things;
    }

    @Override
    public TopRVAdapter.TopVHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //获取布局加载器
         context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        return new TopVHolder(inflater.inflate(R.layout.item_things,parent,false),myClickListener);
    }

    @Override
    public void onBindViewHolder(TopRVAdapter.TopVHolder holder, int position) {
        Thing thing = things.get(position);


        Glide.with(context).load(thing.getIcon()).into(holder.iv_item_things_top_icon);
        holder.tv_item_things_top_name.setText(thing.getName());
        holder.tv_item_things_top_price.setText(thing.getPrice()+"");

    }



    @Override
    public int getItemCount() {
        return things.size();
    }

    //关键是要让viewholder实现点击事件的监听器
    public class TopVHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView iv_item_things_top_icon;
        private TextView tv_item_things_top_price;
        private TextView tv_item_things_top_name;
        private LinearLayout ll_item_things_outer_clickable;

        //自定义的回掉接口
        private MyClickListener listener;



        public TopVHolder(View itemView,MyClickListener listener) {
            super(itemView);
            iv_item_things_top_icon = (ImageView) itemView.findViewById(R.id.iv_item_things_top_icon);
            tv_item_things_top_name  = (TextView) itemView.findViewById(R.id.tv_item_things_top_name);
            tv_item_things_top_price = (TextView)itemView.findViewById(R.id.tv_item_things_top_price);
            ll_item_things_outer_clickable = (LinearLayout)itemView.findViewById(R.id.ll_item_things_outer_clickable);
            //必须让需要实现点击事件的view 注册监听
            ll_item_things_outer_clickable.setOnClickListener(this);
            this.listener = listener;
            Log.e("lister",(listener==null)+" ");

        }

        //模拟listview的onItemCLick 参数有view 和position
        @Override
        public void onClick(View v) {
            listener.myClick(iv_item_things_top_icon,getPosition(),things);

        }
    }



}
