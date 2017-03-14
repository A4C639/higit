package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.DelAddrResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IDelAddr;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/19.
 */


//删除地址的地址的presenter
public class DelAddrPresenter {

    public Observable<DelAddrResEntity> doDelAddr(int aid){

        return NetManager.getServer(NetManager.LOCAL_URL).create(IDelAddr.class).delAddr(aid);
    }
}
