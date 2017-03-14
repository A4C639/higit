package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.UploadPortraitResEntity;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/20.
 */

//上传用户头像
    /*
    * 参数 uid 当前用户id
    * 参数 file 用户要设置的头像文件*/
public interface IUploadPortrait {
    @Multipart
    @POST("Wash/UploadPortrait")
    Observable<UploadPortraitResEntity> uploadPortrait(

            @Part("uid")int uid,
            @Part("file")RequestBody file
            );
}
