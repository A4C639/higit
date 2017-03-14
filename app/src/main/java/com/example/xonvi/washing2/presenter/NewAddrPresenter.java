package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.NewAddrResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.INewAddr;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/9.
 */

/*
* 新增收货人地址的presenter
* */
public class NewAddrPresenter {


    public Observable<NewAddrResEntity> doNewAddr(int uid,String name,String phone,String address){
        return NetManager.getServer(NetManager.LOCAL_URL).create(INewAddr.class).newAddr(uid,name, phone, address);
    }

}
