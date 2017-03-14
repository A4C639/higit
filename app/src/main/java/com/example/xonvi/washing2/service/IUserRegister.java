package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.RegisterResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/4.
 */


//用户注册的业务接口
public interface IUserRegister {
    @FormUrlEncoded
    @POST("Wash/Register")
    Observable<RegisterResEntity> userReg(
            @Field("username")String username,
            @Field("userpass")String userpass

    );
}
