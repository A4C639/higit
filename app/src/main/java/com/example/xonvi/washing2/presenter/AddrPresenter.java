package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.AddrListResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IAddrList;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/8.
 */

//获取用户收货地址的presenter
public class AddrPresenter {
    public Observable<AddrListResEntity> getAddrList(int uid){
        return NetManager.getServer(NetManager.LOCAL_URL).create(IAddrList.class).addrList(uid);
    }
}
