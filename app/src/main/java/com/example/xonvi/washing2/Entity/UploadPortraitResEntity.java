package com.example.xonvi.washing2.Entity;

/**
 * Created by xonvi on 2017/2/20.
 */

//用户头像上传的返回结果
public class UploadPortraitResEntity {
    private int code;
    private String msg;
    private String portrait;

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
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

    @Override
    public String toString() {
        return "UploadPortraitResEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
