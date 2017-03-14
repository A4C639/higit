package com.example.xonvi.washing2.Entity;

/**
 * Created by xonvi on 2017/2/17.
 */

//反馈聊天信息
public class ChatMsg {

    private String msg;
    private String portrait;



    public ChatMsg(String msg, String portrait) {
        this.msg = msg;
        this.portrait = portrait;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @Override
    public String toString() {
        return "ChatMsg{" +
                "msg='" + msg + '\'' +
                ", portrait='" + portrait + '\'' +
                '}';
    }
}
