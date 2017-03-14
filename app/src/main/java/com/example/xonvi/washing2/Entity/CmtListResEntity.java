package com.example.xonvi.washing2.Entity;

import java.util.List;

/**
 * Created by xonvi on 2017/2/14.
 */


//请求评论列表的返回结果
public class CmtListResEntity {

    private int code;
    private String msg;
    private List<CmtEntity> data;

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

    public List<CmtEntity> getData() {
        return data;
    }

    public void setData(List<CmtEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CmtListResEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
