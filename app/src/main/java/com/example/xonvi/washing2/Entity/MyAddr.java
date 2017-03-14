package com.example.xonvi.washing2.Entity;

import java.io.Serializable;

/**
 * Created by xonvi on 2017/2/8.
 */


//收货人信息
public class MyAddr implements Serializable{

    private int id;
    private String name;
    private String phone;
    private String address;
//这个属性用于在ActivityEditAddr中删除和修改地址时保存地址在列表中的位置信息
    private int position;

    public MyAddr() {
    }

    public MyAddr(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MyAddr{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }


}
