package com.example.xonvi.washing2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.CartListEvent;
import com.example.xonvi.washing2.Entity.Thing;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by xonvi on 2017/2/7.
 */

public class CartListAdp extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    private List<Thing> thingList;
    public CartListAdp(List<Thing> thingList){
        this.thingList = thingList;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_cart_list, null);
        }
        CheckBox cb_item_cart_list = (CheckBox) convertView.findViewById(R.id.cb_item_cart_list);
        SimpleDraweeView sdv_item_cart_list_icon = (SimpleDraweeView) convertView.findViewById(R.id.sdv_item_cart_list_icon);
        TextView tv_item_cart_list_name = (TextView)convertView.findViewById(R.id.tv_item_cart_list_name);
        TextView tv_item_cart_list_price = (TextView)convertView.findViewById(R.id.tv_item_cart_list_price);
        ImageView add = (ImageView) convertView.findViewById(R.id.iv_item_cart_list_add);
        ImageView remove = (ImageView) convertView.findViewById(R.id.iv_item_cart_list_remove);
        TextView count_num = (TextView) convertView.findViewById(R.id.tv_item_cart_list_count_num);

        Thing thing = thingList.get(position);

        if(thingList.get(position).getName().equals("")){

        }


        sdv_item_cart_list_icon.setImageURI(thing.getIcon());
        tv_item_cart_list_price.setText(thing.getPrice()+"");
        tv_item_cart_list_name.setText(thing.getName());
        count_num.setText(thing.getCount()+"");
        cb_item_cart_list.setChecked(true);
        cb_item_cart_list.setOnCheckedChangeListener(this);
        add.setOnClickListener(new MyItemClickListener(position));
        remove.setOnClickListener(new MyItemClickListener(position));


        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    //自定义的点击事件实现类 用于获取点击位置
    private class MyItemClickListener implements View.OnClickListener{

        private int pos;
        public MyItemClickListener(int pos){
            this.pos = pos;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //点击 " + " 增加某商品的数目
                case R.id.iv_item_cart_list_add:
                    int counta = thingList.get(pos).getCount();
                    counta++;
                    thingList.get(pos).setCount(counta);
                    //通过事件总线告诉activity物品增加
                    EventBus.getDefault().post(new CartListEvent(CartListEvent.ADD,thingList.get(pos)));
                    CartListAdp.this.notifyDataSetChanged();
                    break;
                //点击 “-”减少商品
                case R.id.iv_item_cart_list_remove:
                    int countr = thingList.get(pos).getCount();
                    countr--;
                    thingList.get(pos).setCount(countr);
                    if(countr==0){
                        //列表空了
                        //移除前将其设置为默认值 不然会出现同意物品移除后下次再添加进来会出现小于1的情况
                        //这里要在最后一个元素移除前把元素通过事件总线传递过去 进行估价变动
                        EventBus.getDefault().post(new CartListEvent(CartListEvent.REMOVE,thingList.get(pos)));
                        thingList.get(pos).setCount(1);
                        thingList.remove(pos);
                        ToastUtil.toast("这项要被移除");
                    }else if(countr>=1){
                        thingList.get(pos).setCount(countr);
                        //通过事件总线告诉Activity物品在做移除物品数
                        EventBus.getDefault().post(new CartListEvent(CartListEvent.REMOVE,thingList.get(pos)));
                    }



                    CartListAdp.this.notifyDataSetChanged();

                    if(thingList.size()<1){
                        EventBus.getDefault().post(new CartListEvent(CartListEvent.EMPTY));
                    }
                    break;
            }
        }
    }


    void hebin(){

    }


}
