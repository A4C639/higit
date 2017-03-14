package com.example.xonvi.washing2.service;



import com.example.xonvi.washing2.Entity.LoginResEntity;
import com.example.xonvi.washing2.Entity.RegisterResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/1/3.
 */

 //用户注册的业务接口
public interface IUserLogin {

    //用户请求注册的方法
    @FormUrlEncoded
    @POST("Wash/Login")
    Observable<LoginResEntity> userLogin(
            @Field("username") String username,
            @Field("userpass") String userpass
    );

}
