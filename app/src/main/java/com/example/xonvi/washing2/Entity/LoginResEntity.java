package com.example.xonvi.washing2.Entity;

/**
 * Created by xonvi on 2017/2/4.
 */

//用户注册结果返回实体类
public class LoginResEntity {

    private int code;
    private String msg;
    private MyUser data;

    public LoginResEntity() {
    }
    public LoginResEntity(int code, String msg, MyUser data) {
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

    public MyUser getData() {
        return data;
    }

    public void setData(MyUser data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginResEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
