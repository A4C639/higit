package com.example.xonvi.washing2.Entity;

import java.util.List;

/**
 * Created by xonvi on 2017/2/18.
 */

//洗衣小技巧列表信息
public class SkillResEntity {

    private int code;
    private String msg;
    private List<SkillEntity> data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SkillEntity> getData() {
        return data;
    }

    public void setData(List<SkillEntity> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SkillResEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
