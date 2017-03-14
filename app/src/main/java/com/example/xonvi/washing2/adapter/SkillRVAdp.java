package com.example.xonvi.washing2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.SkillEntity;
import com.example.xonvi.washing2.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xonvi on 2017/2/18.
 */

public class SkillRVAdp extends RecyclerView.Adapter<SkillRVAdp.SkillListVHolder> {

    private List<SkillEntity> skillEntities;

    public SkillRVAdp(List<SkillEntity> list){
        this.skillEntities = list;
    }

    @Override
    public SkillListVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_aty_skill_list,parent,false);
        return new SkillListVHolder(view) ;
    }

    @Override
    public void onBindViewHolder(SkillListVHolder holder, int position) {
        holder.tv_item_aty_skill_list_desc.setText(skillEntities.get(position).getDescription());
        holder.tv_item_aty_skill_list_keywords.setText(skillEntities.get(position).getKeywords());
    }

    @Override
    public int getItemCount() {
        return skillEntities.size();
    }

    public class SkillListVHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_aty_skill_list_keywords)TextView tv_item_aty_skill_list_keywords;
        @BindView(R.id.tv_item_aty_skill_list_desc)TextView tv_item_aty_skill_list_desc;
        public SkillListVHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
