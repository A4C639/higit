package com.example.xonvi.washing2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.MyAddr;
import com.example.xonvi.washing2.R;

import java.util.List;

/**
 * Created by xonvi on 2017/2/9.
 */
//收货人地址的列表
public class AddrListAdp extends BaseAdapter {

    private List<MyAddr> list;
    public AddrListAdp(List<MyAddr> list){
        this.list = list;
    }
    @Override
    public int getCount() {

        if( list==null){
            return 0;
        }
        return list.size();
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

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null){
           convertView =  inflater.inflate(R.layout.item_addr_list,null);
        }

        TextView receiver_name = (TextView)convertView.findViewById(R.id.tv_item_addr_list_name);
        TextView phone = (TextView)convertView.findViewById(R.id.tv_item_addr_list_phone);
        TextView address = (TextView)convertView.findViewById(R.id.tv_item_addr_list_address);
        TextView default_addr = (TextView)convertView.findViewById(R.id.tv_item_addr_default_address);


        MyAddr addr = list.get(position);

        receiver_name.setText(addr.getName());
        phone.setText(addr.getPhone());
        address.setText(addr.getAddress());
        if(position==0){
            default_addr.setVisibility(View.VISIBLE);
        }


        return convertView;
    }
}
