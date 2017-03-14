package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.ScoreThingResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/3/1.
 */

//请求积分兑换历史列表的业务接口
public interface IScoreHistory {
    @FormUrlEncoded
    @POST("Wash/ScoreHistory")
    Observable<ScoreThingResEntity> scoreHistory(@Field("uid")int uid);


}
