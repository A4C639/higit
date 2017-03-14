package com.example.xonvi.washing2.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.DelAddrResEntity;
import com.example.xonvi.washing2.Entity.MyAddr;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.aty.ActivityUpdateAddr;
import com.example.xonvi.washing2.presenter.DelAddrPresenter;
import com.example.xonvi.washing2.util.ToastUtil;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/9.
 */
//收货人地址的列表的适配器
public class EditAddrAdp extends BaseAdapter{

    private List<MyAddr> list;
    public EditAddrAdp(List<MyAddr> list){
        this.list = list;
    }

    public void setList(List<MyAddr> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public List<MyAddr> getList() {
        return list;
    }
    //修改地址的点击回掉事件
    private IUpdateCallBack callBack;
    public interface IUpdateCallBack{
        void updateCallBack(int pos);
    }
    public void setCallBack(IUpdateCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
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
           convertView =  inflater.inflate(R.layout.item_edit_addr_list,null);
        }

        TextView receiver_name = (TextView)convertView.findViewById(R.id.tv_item_addr_list_name);
        TextView phone = (TextView)convertView.findViewById(R.id.tv_item_addr_list_phone);
        TextView address = (TextView)convertView.findViewById(R.id.tv_item_addr_list_address);
        TextView default_addr = (TextView)convertView.findViewById(R.id.tv_item_addr_default_address);
        //删
        TextView del = (TextView)convertView.findViewById(R.id.tv_item_edit_addr_del);
        //改
        TextView edit = (TextView)convertView.findViewById(R.id.tv_item_edit_addr_edit);

        MyAddr addr = list.get(position);

        receiver_name.setText(addr.getName());
        phone.setText(addr.getPhone());
        address.setText(addr.getAddress());
        if(position==0){
            default_addr.setVisibility(View.VISIBLE);
        }

        del.setOnClickListener(new EditAddrListener(position));
        edit.setOnClickListener(new EditAddrListener(position));


        return convertView;
    }


    private class EditAddrListener implements View.OnClickListener {
        int position;
        public EditAddrListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //删除地址
                case R.id.tv_item_edit_addr_del:
                    delAddr(list.get(position));
                    break;
                //编辑地址
                case R.id.tv_item_edit_addr_edit:
                    //去适配器外面处理(ActivityEditAdddr中去处理方便)
                    callBack.updateCallBack(position);
                    break;
            }
        }

        //删除地址
        private void delAddr(MyAddr addr) {
            new DelAddrPresenter().doDelAddr(addr.getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<DelAddrResEntity>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            Log.e("deladdreerror",e.toString());
                        }
                        @Override
                        public void onNext(DelAddrResEntity entity) {
                            ToastUtil.toast(entity.getMsg());
                            if (entity.getCode()==1){//删除成功 刷新列表(这里刷新影响的是ActivityEditAddr中的地址列表)
                                list.remove(position);
                                EditAddrAdp.this.notifyDataSetChanged();
                            }
                        }
                    });

        }
    }

}
