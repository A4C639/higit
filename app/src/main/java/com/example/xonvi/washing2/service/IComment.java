package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.CommentResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/14.
 */

/*
* 用户评价的业务接口
* 参数 uname 用户名
* 参数 portrait 用户头像
* 参数 comment 评论内容
* 参数 time 时间*/

public interface IComment {

    @FormUrlEncoded
    @POST("Wash/Comment")
    Observable<CommentResEntity> comment(@Field("uname")String uname,
                                         @Field("portrait")String portrait,
                                         @Field("comment")String comment,
                                         @Field("time")String time);
}
