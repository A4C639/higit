package com.example.xonvi.washing2.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.ShopItem;
import com.example.xonvi.washing2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by xonvi on 2017/2/15.
 */
//商城列表的适配器
public class ShopRVAdp extends RecyclerView.Adapter<ShopRVAdp.MyShopVHolder>{

    private List<ShopItem> shopItems;
    public  ShopRVAdp(List<ShopItem> shopItems){
        this.shopItems = shopItems;
    }


    private ShopItemClick itemClick;
    public void setItemClick(ShopItemClick itemClick) {
        this.itemClick = itemClick;
    }
    public interface ShopItemClick{
        void shopItemClick(int pos,View view);
    }


    @Override
    public MyShopVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MyShopVHolder(inflater.inflate(R.layout.item_aty_shop_list,parent,false),this.itemClick);
    }

    @Override
    public void onBindViewHolder(MyShopVHolder holder, int position) {

        ShopItem item = shopItems.get(position);
        holder.sdv_icon.setImageURI(item.getIcon());
        holder.tv_score.setText("积分 "+item.getScore()+"");
        holder.tv_desc.setText(item.getDesc());
        if(item.getRest()<1){
            holder.ll_shop_item.setClickable(false);
            holder.tv_rest.setText("售罄");
        }else {
            holder.tv_rest.setText(item.getRest()+"");
        }


    }

    @Override
    public int getItemCount() {
        return shopItems.size();
    }

    public class MyShopVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SimpleDraweeView sdv_icon;
        TextView tv_desc;
        TextView tv_score;
        TextView tv_rest;
        LinearLayout ll_shop_item;
        ShopItemClick shopItemClick;

        public MyShopVHolder(View itemView,ShopItemClick shopItemClick) {
            super(itemView);
            this.shopItemClick = shopItemClick;
            ll_shop_item = (LinearLayout) itemView.findViewById(R.id.ll_shop_item);
            sdv_icon = (SimpleDraweeView) itemView.findViewById(R.id.sdv_aty_item_shop_icon);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_aty_item_shop_desc);
            tv_score = (TextView) itemView.findViewById(R.id.tv_aty_item_shop_score);
            tv_rest = (TextView)itemView.findViewById(R.id.tv_aty_item_shop_rest);

            ll_shop_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            shopItemClick.shopItemClick(getPosition(),v);
        }
    }
}
