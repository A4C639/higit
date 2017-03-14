package com.example.xonvi.washing2.Entity;

import java.io.Serializable;

/**
 * Created by xonvi on 2017/1/3.
 */

//用户请求注册返回的实体类
public class RegisterResEntity implements Serializable{

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
        return "RegisterResEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
