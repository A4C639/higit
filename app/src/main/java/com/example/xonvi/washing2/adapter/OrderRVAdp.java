package com.example.xonvi.washing2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.MyAddr;
import com.example.xonvi.washing2.Entity.Thing;
import com.example.xonvi.washing2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by xonvi on 2017/2/8.
 */

//订单确认的列表适配器
public class OrderRVAdp extends RecyclerView.Adapter<OrderRVAdp.OrderVHolder>{


    //点击回掉接口
    private MyClickListener myClickListener;
    public void setMyClickListener(MyClickListener myClickListener){
        this.myClickListener = myClickListener;
    }
    public interface MyClickListener{
        void onHeaderClick(int pos,View view);
    }


    private List<Thing> thingList;

    private MyAddr myAddr;
    public void setMyAddr(MyAddr myAddr) {
        this.myAddr = myAddr;
        this.notifyDataSetChanged();
    }

    public OrderRVAdp(List<Thing> list, MyAddr myAddr){
        this.thingList = list;
        this.myAddr = myAddr;
    }

    @Override
    public OrderVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        //判断位置 加载对应的view
        if (viewType==0) {
            //第一个要加载header
            view = inflater.inflate(R.layout.item_order_list_header, parent, false);
        }
            //其余的都加载正常的thing item
         else{
            view = inflater.inflate(R.layout.item_order_list_thing,parent,false);
        }
        return new OrderVHolder(view,myClickListener);
    }

    @Override
    public void onBindViewHolder(OrderVHolder holder, int position) {

        if(position==0){//header 加载收货人地址信息
            //填充收货人信息

            Log.e("pos0","myaddr"+(myAddr==null)+" "+(holder.tv_receiver_name==null));
            holder.tv_receiver_name.setText(myAddr.getName());
            holder.tv_address.setText(myAddr.getAddress());
            holder.tv_phone.setText(myAddr.getPhone());



        }else {
            //因为第一个位置被header用了 所以减回来(事实上是集合中的第一个元素没有用到)
            position--;
            Thing thing = thingList.get(position);
            holder.sdv_thing_name.setImageURI(thing.getIcon());
            holder.tv_thing_name.setText(thing.getName());
            holder.tv_thing_price.setText(thing.getPrice()+"");
            holder.tv_thing_count.setText(thing.getCount()+"");
        }



    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return thingList.size()+1;
    }


    public class OrderVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //thing item
        SimpleDraweeView sdv_thing_name;
        TextView tv_thing_name;
        TextView tv_thing_price;
        TextView tv_thing_count;

        //header
        TextView tv_receiver_name;
        TextView tv_phone;
        TextView tv_address;
        LinearLayout ll_headder;
        MyClickListener myClickListener;


        public OrderVHolder(View itemView,MyClickListener myClickListener) {
            super(itemView);

            switch (itemView.getId()){

                case R.id.layout_header_mkorder:
                    //header
                    tv_receiver_name = (TextView) itemView.findViewById(R.id.tv_aty_mkorder_receiver);
                    tv_phone=(TextView)itemView.findViewById(R.id.tv_aty_mkorder_phone);
                    tv_address = (TextView)itemView.findViewById(R.id.tv_item_order_list_address);
                    ll_headder = (LinearLayout) itemView.findViewById(R.id.ll_rv_mkorder_list_header);
                    Log.e("headernull",(ll_headder==null)+"");
                    this.myClickListener = myClickListener;
                    ll_headder.setOnClickListener(this);
                    break;
                case R.id.layout_thing_mkorder:

                    //normal item
                    sdv_thing_name = (SimpleDraweeView) itemView.findViewById(R.id.sdv_item_order_list_thing_icon);
                    tv_thing_name = (TextView) itemView.findViewById(R.id.tv_item_order_list_thing_name);
                    tv_thing_price = (TextView) itemView.findViewById(R.id.tv_item_order_list_thing_price);
                    tv_thing_count = (TextView) itemView.findViewById(R.id.tv_item_order_list_thing_count);
                    break;

            }




        }

        @Override
        public void onClick(View v) {

            myClickListener.onHeaderClick(getPosition(),v);


        }

    }


}
