package com.example.xonvi.washing2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.ShopItem;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xonvi on 2017/3/1.
 */

//积分兑换历史记录列表的适配器
public class ScoreHisRVAdp extends RecyclerView.Adapter<ScoreHisRVAdp.ScoreHisVHolder>{

    private List<ShopItem> scoreThings;
    public ScoreHisRVAdp(List<ShopItem> scoreThings){
        this.scoreThings =scoreThings;
    }

    private MyScoreHisClickListener myScoreHisClickListener;
    public void setMyScoreHisClickListener(MyScoreHisClickListener listener){
        this.myScoreHisClickListener = listener;
    }
    public interface MyScoreHisClickListener{
        void myScoreHisItemClick(int position,View view);
    }

    //移除积分兑换列表的元素
    public boolean delElement(int position){

        if( scoreThings.remove(scoreThings.get(position))){
            this.notifyDataSetChanged();
            ToastUtil.toast("移除成功");
            return true;
        }else {
            ToastUtil.toast("移除失败");
            return false;
        }
    }

    @Override
    public ScoreHisVHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_aty_scorehistory_list, parent, false);
        return new ScoreHisVHolder(view,this.myScoreHisClickListener);
    }

    @Override
    public void onBindViewHolder(ScoreHisVHolder holder, int position) {
        ShopItem scoreThing = scoreThings.get(position);

        holder.sdv_icon.setImageURI(scoreThing.getIcon());
        Log.e("icon",scoreThing.getIcon());
        holder.tv_desc.setText(scoreThing.getDesc());
        holder.tv_score.setText(scoreThing.getScore()+"积分");

    }

    @Override
    public int getItemCount() {
        return scoreThings.size();
    }

    public class ScoreHisVHolder extends RecyclerView.ViewHolder {

        private MyScoreHisClickListener listener;
        @BindView(R.id.sdv_item_aty_scorehistory_icon)SimpleDraweeView sdv_icon;
        @BindView(R.id.tv_aty_item_scorehistory_score)TextView tv_score;
        @BindView(R.id.tv_aty_item_scorehistory_desc)TextView tv_desc;
        @OnClick(R.id.iv_item_aty_scorehistory_del)
        public void click(View view){
            listener.myScoreHisItemClick(getPosition(),view);
        }
        public ScoreHisVHolder(View itemView,MyScoreHisClickListener listener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.listener = listener;
        }
    }
}
