package com.example.xonvi.washing2.Entity;

/**
 * Created by xonvi on 2017/2/14.
 */


//评论对象的实体类
public class CmtEntity {

    private String uname;
    private String portrait;
    private String comment;
    private String time;


    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CmtEntity() {
    }

    public CmtEntity(String uname, String portrait, String comment, String time) {
        this.uname = uname;
        this.portrait = portrait;
        this.comment = comment;
        this.time = time;
    }

    @Override
    public String toString() {
        return "CmtEntity{" +
                "uname='" + uname + '\'' +
                ", portrait='" + portrait + '\'' +
                ", comment='" + comment + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
