package com.example.xonvi.washing2.Entity;

import java.util.List;

/**
 * Created by xonvi on 2017/2/8.
 */


//收货地址列表返回数据模型
public class AddrListResEntity {


    private int code;
    private String msg;
    private List<MyAddr> data;

    public AddrListResEntity() {
    }

    public AddrListResEntity(int code, String msg, List<MyAddr> data) {
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

    public List<MyAddr> getData() {
        return data;
    }

    public void setAddrList(List<MyAddr> addrList) {
        this.data = addrList;
    }

    @Override
    public String toString() {
        return "AddrListResEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", addrList=" + data +
                '}';
    }
}
