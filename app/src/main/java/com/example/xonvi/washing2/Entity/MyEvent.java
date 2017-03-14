package com.example.xonvi.washing2.Entity;

/**
 * Created by xonvi on 2017/2/4.
 */



/*
* LOGIN 登陆成功的事件
* LOGUOT 和注销账号的事件
* CHARGE_SUCCESS 充值成功
* SCORE_COST_SUCCESS消耗积分成功
* */

public class MyEvent {
    public int event;
    public MyEvent(int event){
        this.event = event;

    }

    public MyEvent(int event,String portrait){
        this.event = event;
        this.portrait = portrait;

    }
    private String portrait;

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public static final int UPDATE_ADDR_FINISH=0x4;
    public static final int LOGIN=0x1;
    public static final int LOGUOT=0x2;
    public static final int CHARGE_SUCCESS = 0x3;
    public static final int DEL_ADDR=0x5;
    public  static final int UPDATE_ADDR_AFTER_ATY_EDT_ADDR=0x6;
    public static final int PORTRAIT_UPLOAD_SUCCESS = 0x7;
    public static final int SCORE_COST_SUCCESS=0x8;

}
