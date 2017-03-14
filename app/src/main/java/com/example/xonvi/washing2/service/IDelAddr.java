package com.example.xonvi.washing2.service;

/**
 * Created by xonvi on 2017/2/19.
 */




import com.example.xonvi.washing2.Entity.DelAddrResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 删除地址的业务接口
 * 参数 aid 地址的id
 */
public interface IDelAddr {
    @FormUrlEncoded
    @POST("Wash/DelAddr")
    Observable<DelAddrResEntity> delAddr(@Field("aid") int aid);
}
