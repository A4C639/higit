package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.ScoreHisDelResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/3/1.
 */

//删除兑换积分历史的记录的业务接口
public interface IScoreHisDel {

    @FormUrlEncoded
    @POST("Wash/ScoreHisDel")
    Observable<ScoreHisDelResEntity> delHis(@Field("id") int id);
}
