package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.ChargeResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/15.
 */

/*
* 参数 uid 当前用的id
* 参数 charge 充值的金额*/
public interface ICharge {

    @FormUrlEncoded
    @POST("Wash/Charge")
    Observable<ChargeResEntity> charge(@Field("uid")int uid, @Field("charge")int charge);
}
