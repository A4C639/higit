package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.ChargeResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.ICharge;
import com.example.xonvi.washing2.service.ICommentList;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/15.
 */


//用户充值的presenter
public class ChargePresenter {
    public Observable<ChargeResEntity> doCharge(int uid,int charge){
        return NetManager.getServer(NetManager.LOCAL_URL).create(ICharge.class).charge(uid,charge);
    }
}
