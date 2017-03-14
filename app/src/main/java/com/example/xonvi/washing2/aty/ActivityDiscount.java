package com.example.xonvi.washing2.aty;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.Discount;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.DiscountRVAdp;
import com.example.xonvi.washing2.fragment.Fragment_Identity;
import com.example.xonvi.washing2.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xonvi on 2017/2/27.
 */

//优惠券
public class ActivityDiscount extends BaseActivity implements DiscountRVAdp.MyDiscountCheckedListener {

    //保存被选中的优惠券
    private List<Integer> integerList;
    //优惠券列表
    private List<Discount> discount;
    //标记启动Activity的来源
    private int pattern = 0;
    //优惠券列表
    @BindView(R.id.rv_aty_discount)RecyclerView rv_aty_discount;
    //确认选取优惠券
    @BindView(R.id.tv_aty_discount_pickup)TextView tv_aty_discount_pickup;


    @OnClick({R.id.iv_aty_discount_back,R.id.tv_aty_discount_pickup})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_discount_back:
                this.finish();
                break;
            //确定选取优惠券
            case R.id.tv_aty_discount_pickup:
                if(integerList.size()>1){
                    ToastUtil.toast("该商品一次仅能使用一张优惠券");
                    return;
                }
                ToastUtil.toast("确定选取");
                setResult(RESULT_OK,new Intent().putExtra("discount",discount.get(integerList.get(0))));
                this.finish();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);
        ButterKnife.bind(this);

        setPattern();
        initDiscountList();
    }


    //根据启动Activity的来源设置页面细节---------------------------------------------------------------
    private void setPattern() {

        pattern = getIntent().getIntExtra("PATTERN",0);
        if(pattern== Fragment_Identity.DISCOUNT_PATTERN_SCAN){
            ToastUtil.toast(pattern+"-------------------");
            tv_aty_discount_pickup.setVisibility(View.GONE);


        }else if (pattern == ActivityMkOrder.DISCOUNT_PATTERN_PICK){
            ToastUtil.toast(pattern+"-------------------");
            tv_aty_discount_pickup.setVisibility(View.VISIBLE);
        }else {
            ToastUtil.toast("未知打开方式");
            this.finish();
        }

    }


    //初始化 优惠券列表
    private void initDiscountList() {
        integerList = new ArrayList<>();
        discount = new ArrayList<>();
        discount.add(new Discount(1,"1元券",1));
        discount.add(new Discount(2,"2元券",2));
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_aty_discount.setLayoutManager(manager);
        DiscountRVAdp discountRVAdp = new DiscountRVAdp(discount,pattern);
        discountRVAdp.setMyDiscountClickListener(this);
        rv_aty_discount.setAdapter(discountRVAdp);

    }

    //优惠券项的选中事件---------------------------------------------------------------------------------
    @Override
    public void discountCheckChanged(int position, CompoundButton view, boolean isChecked) {
        Log.e("xuanzhongle",position+"  "+isChecked+"  ");
        if(isChecked){
            integerList.add(position);
            tv_aty_discount_pickup.setClickable(true);
            tv_aty_discount_pickup.setTextColor(Color.parseColor("#FFFFFF"));
        }else {

            //判断列表的长度
            if(integerList.size()==1){
                //为解决 当且仅当list中只有一个元素，移除的元素引又大于list长度时，报下标越界的问题
                integerList.clear();
            }else {
                integerList.remove(position);
            }

            //如果列表为空 使用户不能提交选取优惠券
            if(integerList.isEmpty()){
                tv_aty_discount_pickup.setClickable(false);
                tv_aty_discount_pickup.setTextColor(Color.parseColor("#c0c0c0"));
            }else {
                //列表中有元素就可以提交选取的优惠券
                tv_aty_discount_pickup.setClickable(true);
                tv_aty_discount_pickup.setTextColor(Color.parseColor("#FFFFFF"));
            }
        }
        Log.e("discountlist",integerList.toString());

    }


}
