package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.UpdateAddrResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IUpdateAddr;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/19.
 */


//修改收货人地址的presenter

public class UpdateAddrPresenter {
    public Observable<UpdateAddrResEntity> doUpdateAddr(int aid,String addr,String name,String phone ){
        return NetManager.getServer(NetManager.LOCAL_URL).create(IUpdateAddr.class).updateAddr(aid,addr,name,phone);

    }
}
