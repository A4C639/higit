package com.example.xonvi.washing2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by xonvi on 2017/2/14.
 */

import com.example.xonvi.washing2.Entity.CmtEntity;
import com.example.xonvi.washing2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;//评论列表的适配器
public class CmtListAdp extends BaseAdapter {


    private List<CmtEntity> cmtEntities;
    public CmtListAdp(List<CmtEntity> cmtEntities){
        this.cmtEntities = cmtEntities;
    }

    @Override
    public int getCount() {
        return cmtEntities.size();
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
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            convertView = inflater.inflate(R.layout.item_aty_cmtlist,null);
        }

        SimpleDraweeView sdv = (SimpleDraweeView) convertView.findViewById(R.id.sdv_item_aty_cmtlist_icon);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_item_aty_cmtlist_name);
        TextView tv_comment = (TextView) convertView.findViewById(R.id.tv_aty_item_cmtlist_comment);
        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_aty_item_cmtlist_time);

        CmtEntity entity = cmtEntities.get(position);

        sdv.setImageURI(entity.getPortrait());
        tv_comment.setText(entity.getComment());
        tv_name.setText(entity.getUname());
        tv_time.setText(entity.getTime());


        return convertView;
    }





}

