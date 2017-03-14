package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.Thing;

import java.util.List;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/5.
 */

//上衣的列表请求业务借口

public interface ITopList {

//    @FormUrlEncoded  如果使用了这个注解必须至少有一个@field
     @POST("Wash/TopList")
    Observable<List<Thing>> topList();
}
