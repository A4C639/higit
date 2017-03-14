package com.example.xonvi.washing2.Entity;

import java.io.Serializable;

/**
 * Created by xonvi on 2017/2/6.
 */

//物品对象
public class Thing implements Serializable{

    private String name;
    private int price;
    private String icon;
    private int count=1;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    /*当我将物品添加到列表想让他们如果类别相同的话自动合并
    *当然理论上来讲应该是理由商品id作为唯一标识
    * 我这里是使用的商品的名称 这样是不规范的
    * */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Thing){
            Thing thing = (Thing) o;
            //名字相同
            if(thing.getName().equals(this.getName())){
                return true;
            }
            return false;

        }else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Thing{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", icon='" + icon + '\'' +
                '}';
    }
}
