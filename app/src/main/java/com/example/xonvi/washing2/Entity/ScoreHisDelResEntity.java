package com.example.xonvi.washing2.Entity;

/**
 * Created by xonvi on 2017/3/1.
 */

//请求删除积分兑换历史记录的返回模型
public class ScoreHisDelResEntity {

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
        return "ScoreHisDelResEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
