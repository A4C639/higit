package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.AddrListResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/8.
 */
//收货人地址的业务接口
    /*
    参数 uid 当前用户的id
    * */
public interface IAddrList {

    @FormUrlEncoded
    @POST("Wash/AddrList")
    Observable<AddrListResEntity> addrList(@Field("uid")int uid);

}
