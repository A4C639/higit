package com.example.xonvi.washing2.Entity;


import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by xonvi on 2017/2/7.
 */


//购物车操作的事件 以及操作类型描述
public class CartListEvent {

    //我这里内置几种操作类型ADD ,REMOVE和 EMPTY
    //这是popwindow中listview中已经添加到购物车列表的物品数目增加
    public static final int ADD = 0x1;
    public static final int REMOVE = 0x2;
    public static final int EMPTY = 0x3;
    //这是从fragment页面添加物品到购物车
    public static final int ADD_TO_CART = 0x4;
    public static final int SUBMIT_COMPLETED = 0x5;

    //记录操作类型
    public int TYPE;
    //具体物品对象
    private Thing thing;

    private ImageView iv;


    public CartListEvent(int TYPE) {
        this.TYPE = TYPE;
    }

    public CartListEvent(Thing thing) {
        this.thing = thing;
    }

    public CartListEvent(int TYPE, Thing thing){
        this.TYPE = TYPE;
        this.thing = thing;
    }

    public CartListEvent(int TYPE, Thing thing, ImageView iv) {
        this.TYPE = TYPE;
        this.thing = thing;
        this.iv = iv;
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    public ImageView getIv() {
        return iv;
    }

    public void setIv(ImageView iv) {
        this.iv = iv;
    }
}
