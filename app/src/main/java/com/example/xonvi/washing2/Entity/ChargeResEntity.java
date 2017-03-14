package com.example.xonvi.washing2.Entity;

/**
 * Created by xonvi on 2017/2/15.
 */

//用户充值结果的实体类
public class ChargeResEntity {

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
        return "ChargeResEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
