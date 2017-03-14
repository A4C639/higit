package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.ShopItem;
import com.example.xonvi.washing2.Entity.ShopItemEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IShopList;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/15.
 */

//获取 商城列表的presenter

public class ShopPresenter {
    public Observable<ShopItemEntity> doShopList(){
        return NetManager.getServer(NetManager.LOCAL_URL).create(IShopList.class).shopList();
    }
}
