package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.UpdateAddrResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/19.
 */

//修改地址的业务接口
/*
* 参数 aid 地址的id
* 参数 addr 收货人详细地址
* 参数 name 收货人姓名
* 参数 phone 收货人*/
public interface IUpdateAddr {
    @FormUrlEncoded
    @POST("Wash/UpdateAddr")
    Observable<UpdateAddrResEntity> updateAddr(@Field("aid") int aid,
                                               @Field("addr") String addr,
                                               @Field("name") String name,
                                               @Field("phone") String phone);
}
