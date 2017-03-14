package com.example.xonvi.washing2.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.ChatMsg;
import com.example.xonvi.washing2.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xonvi on 2017/2/17.
 */

//反馈聊天列表的适配器
public class FeedBackRVAdp extends RecyclerView.Adapter<FeedBackRVAdp.FeedBackVHolder> {

    private List<ChatMsg> msgList;
    public void setMsgList(ChatMsg chatMsg){
        msgList.add(chatMsg);
        this.notifyDataSetChanged();
        Log.e("list",msgList.size()+"  ");
    }

    public FeedBackRVAdp (){
        this.msgList = new ArrayList<>();
    }

    @Override
    public FeedBackVHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_aty_feedback_msg, parent, false);

        return new FeedBackVHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedBackVHolder holder, int position) {
        if(msgList.isEmpty()){
           return;
        }
        holder.sdv_portrait.setImageURI(msgList.get(position).getPortrait());
        holder.tv_msg.setText(msgList.get(position).getMsg());

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public class FeedBackVHolder extends RecyclerView.ViewHolder {
        private TextView tv_msg;
        private SimpleDraweeView sdv_portrait;
        public FeedBackVHolder(View itemView) {
            super(itemView);

            tv_msg = (TextView) itemView.findViewById(R.id.tv_aty_feedback_msg);
            sdv_portrait = (SimpleDraweeView) itemView.findViewById(R.id.sdv_aty_feedback_portrait);
        }
    }
}
