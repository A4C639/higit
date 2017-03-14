package com.example.xonvi.washing2.Entity;

import java.util.List;

/**
 * Created by xonvi on 2017/2/15.
 */


//请求商城商品列表的返回实体类
public class ShopItemEntity {

    private int code;
    private String msg;
    private List<ShopItem> data;

    public ShopItemEntity() {
    }

    public ShopItemEntity(int code, String msg, List<ShopItem> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ShopItem> getData() {
        return data;
    }

    public void setData(List<ShopItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ShopItemEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
