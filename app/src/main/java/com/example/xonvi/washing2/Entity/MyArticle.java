package com.example.xonvi.washing2.Entity;

import java.util.List;

/**
 * Created by xonvi on 2017/2/9.
 */

/*用户提交的订单对象
分为两部分数据
address收货人地址及其他信息
thinglist 预定清洗物品列表
total订单总额




* */
public class MyArticle {

    private int id;
    private int uid;
    private MyAddr address;
    private String thinglist;
    private String total;
    private String time;

    public MyArticle() {
    }

    public MyArticle(MyAddr address, String thingList,int uid,String total) {
        this.address = address;
        this.thinglist = thingList;
        this.uid = uid;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public MyAddr getAddress() {
        return address;
    }

    public void setAddress(MyAddr address) {
        this.address = address;
    }

    public String getThingList() {
        return thinglist;
    }

    public void setThingList(String thingList) {
        this.thinglist = thingList;
    }
}
