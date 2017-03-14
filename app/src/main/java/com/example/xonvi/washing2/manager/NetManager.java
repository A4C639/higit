package com.example.xonvi.washing2.manager;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xonvi on 2017/2/3.
 */


//网络请求的管理
    //提供获取retrofit对象的工厂方法
public class NetManager {

    public static final String LOCAL_URL = "http://192.168.155.1:8080/";
    public static Retrofit getServer(String url){
        Retrofit server = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return server;
    }



}
