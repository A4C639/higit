package com.example.xonvi.washing2.Entity;

/**
 * Created by xonvi on 2017/2/19.
 */

//修改密码返回结果的实体类
public class EditPwdResEntity {

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
        return "EditPwdResEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
