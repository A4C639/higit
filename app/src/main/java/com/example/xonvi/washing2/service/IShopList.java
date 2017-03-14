package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.ShopItemEntity;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/15.
 */

//请求商城列表的接口
public interface IShopList {
    @POST("Wash/ShopList")
    Observable<ShopItemEntity> shopList();

}
