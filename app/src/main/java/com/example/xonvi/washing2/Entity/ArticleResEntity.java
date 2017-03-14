package com.example.xonvi.washing2.Entity;

import java.util.List;

/**
 * Created by xonvi on 2017/2/9.
 */

//用户单列表的返回数据模型
public class ArticleResEntity {

    private int code;
    private String msg;
    private List<MyArticle> data;

    public ArticleResEntity() {
    }

    public ArticleResEntity(int code, String msg, List<MyArticle> data) {
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

    public List<MyArticle> getData() {
        return data;
    }

    public void setData(List<MyArticle> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ArticleResEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
