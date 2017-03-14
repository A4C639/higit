package com.example.xonvi.washing2.Entity;

import java.io.Serializable;

/**
 * Created by xonvi on 2017/2/28.
 */

//优惠券的数据模型
public class Discount implements Serializable{

    private int id;
    private String desc;
    private int discount;

    public Discount() {
    }

    public Discount(String desc, int discount) {
        this.desc = desc;
        this.discount = discount;
    }

    public Discount(int id, String desc, int discount) {
        this.id = id;
        this.desc = desc;
        this.discount = discount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", discount=" + discount +
                '}';
    }
}
