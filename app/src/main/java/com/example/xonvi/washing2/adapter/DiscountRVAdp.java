package com.example.xonvi.washing2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.Discount;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.aty.ActivityMkOrder;
import com.example.xonvi.washing2.fragment.Fragment_Identity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by xonvi on 2017/2/28.
 */

//优惠券
public class DiscountRVAdp extends RecyclerView.Adapter<DiscountRVAdp.DiscountVHolder> {

    private MyDiscountCheckedListener myDiscountCheckedListener;
    public void setMyDiscountClickListener(MyDiscountCheckedListener listener){
        this.myDiscountCheckedListener =listener;
    }
    public interface MyDiscountCheckedListener{
        void discountCheckChanged(int position,CompoundButton view,boolean isChecked);
    }

    //启动Activity的来源标记
    private int pattern;
    //优惠券列表
    private List<Discount> discounts;
    public DiscountRVAdp(List<Discount> discounts, int pattern){

        this.pattern = pattern;
        this.discounts = discounts;
    }
    @Override
    public DiscountVHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_aty_discount, parent, false);
        return new DiscountVHolder(view,myDiscountCheckedListener);
    }

    @Override
    public void onBindViewHolder(DiscountVHolder holder, int position) {

        if(pattern== Fragment_Identity.DISCOUNT_PATTERN_SCAN){
            holder.cb_item_aty_discount_list.setVisibility(View.GONE);
        }else if(pattern == ActivityMkOrder.DISCOUNT_PATTERN_PICK){
            holder.cb_item_aty_discount_list.setVisibility(View.VISIBLE);
        }

        holder.tv_aty_item_discount_list_desc.setText(discounts.get(position).getDesc());
        holder.tv_aty_item_discount_list_value.setText(discounts.get(position).getDiscount()+"");
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    public class DiscountVHolder extends RecyclerView.ViewHolder {
        private MyDiscountCheckedListener listener;
        //优惠具体的价格值
        @BindView(R.id.tv_aty_item_discount_list_value)TextView tv_aty_item_discount_list_value;
        //优惠券的描述
        @BindView(R.id.tv_aty_item_discount_list_desc)TextView tv_aty_item_discount_list_desc;
        //复选框
        @BindView(R.id.cb_item_aty_discount_list)CheckBox cb_item_aty_discount_list;
        @OnCheckedChanged(R.id.cb_item_aty_discount_list)
        public void onCheckChanged(CompoundButton compoundButton,boolean isChecked){
            listener.discountCheckChanged(getPosition(),compoundButton,isChecked);
        }
        public DiscountVHolder(View itemView,MyDiscountCheckedListener listener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.listener = listener;
        }
    }
}
