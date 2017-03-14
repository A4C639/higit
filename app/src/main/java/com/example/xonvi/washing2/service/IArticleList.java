package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.ArticleResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/10.
 */

/*
@ 参数 uid 当前用户的id

* */

public interface IArticleList {
    @FormUrlEncoded
    @POST("Wash/ArticleList")
    Observable<ArticleResEntity> articleList(
            @Field("uid")int uid

    );

}
