package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.NewAddrResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/9.
 */

/*
* 新增收货人地址的业务接口
* @ 参数 收货人姓名
* @ 参数 收货人电话
* @ 参数 收货人详细地址
* @ 参数 地址所属的用户
* @ 返回值 Observable观察者对象
* */
public interface INewAddr {

    @FormUrlEncoded
    @POST("Wash/NewAddr")
    Observable<NewAddrResEntity> newAddr(
            @Field("uid")int uid,
            @Field("uname") String name,
            @Field("phone")String phone,
            @Field("address")String address);
}
