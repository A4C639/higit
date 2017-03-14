package com.example.xonvi.washing2.Entity;

/**
 * Created by xonvi on 2017/2/15.
 */


//积分商城的商品对象
public class ShopItem {

    private int id;
    private String desc;
    private String icon;
    private int score;
    private int rest;

    public ShopItem() {
    }

    public ShopItem(String desc, String icon, int score,int rest,int id) {
        this.desc = desc;
        this.icon = icon;
        this.score = score;
        this.rest  = rest;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRest() {
        return rest;
    }

    public void setRest(int rest) {
        this.rest = rest;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ShopItem{" +
                "desc='" + desc + '\'' +
                ", icon='" + icon + '\'' +
                ", score=" + score +
                ", rest=" + rest +
                '}';
    }
}
