package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.EditPwdResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/19.
 */


//修改密码的业务接口
public interface IEditPwd {
    @FormUrlEncoded
    @POST("Wash/EditPwd")
    Observable<EditPwdResEntity> editPwd(@Field("uid")int uid,
                                         @Field("oldpass")String oldpass,
                                         @Field("newpass")String newpass);
}
