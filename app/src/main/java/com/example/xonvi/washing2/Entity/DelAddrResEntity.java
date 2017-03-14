package com.example.xonvi.washing2.Entity;

/**
 * Created by xonvi on 2017/2/19.
 */

//s删除地址的返回结果
public class DelAddrResEntity {
    private int code;
    private String msg;

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

    @Override
    public String toString() {
        return "DelAddrResEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
