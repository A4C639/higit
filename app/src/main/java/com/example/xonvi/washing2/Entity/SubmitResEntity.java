package com.example.xonvi.washing2.Entity;

/**
 * Created by xonvi on 2017/2/9.
 */
//提交订单返回的数据模型

public class SubmitResEntity {

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

    public SubmitResEntity() {
    }

    public SubmitResEntity(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
