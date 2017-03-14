package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.CmtEntity;
import com.example.xonvi.washing2.Entity.CmtListResEntity;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/14.
 */

/*
* 直接返回评论列表*/

public interface ICommentList {
    @POST("Wash/CommentList")
    Observable<CmtListResEntity> commentList();
}
