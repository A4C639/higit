package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.SubmitResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/9.
 */

/*
* 用户提交订单的接口
* @ uid 当前用户的id
* @ phone用户的电话
* @ address用户收货地址
* @ thinglist购买物品列表
* @ total 总价
* @ time 下单时间
* */

public interface ISubmitList {

    @FormUrlEncoded
    @POST("Wash/SubmitList")
    Observable<SubmitResEntity> submitList(@Field("uid")int uid,
                                           @Field("uname")String uname,
                                           @Field("phone")String phone,
                                           @Field("address")String address,
                                           @Field("thinglist")String thinglist,
                                           @Field("total")String total,
                                           @Field("time")String time);
}
