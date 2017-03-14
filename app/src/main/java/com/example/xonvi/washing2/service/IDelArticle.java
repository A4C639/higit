package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.DelArticleResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/14.
 */

/*
* 删除订单的接口
* 参数 id 确定要删除的订单的id
* */

public interface IDelArticle {
    @FormUrlEncoded
    @POST("Wash/DelArticle")
    Observable<DelArticleResEntity> delArticle(@Field("id")int id);
}
