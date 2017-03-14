package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.RegisterResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IUserRegister;

import rx.Observable;

import static com.example.xonvi.washing2.manager.NetManager.LOCAL_URL;

/**
 * Created by xonvi on 2017/2/4.
 */

//用户注册的presenter帮助类
public class RegPresenter {

    public Observable<RegisterResEntity> doUserReg(String username,String userpass){

        return NetManager.getServer(LOCAL_URL).create(IUserRegister.class).userReg(username,userpass);
    }
}
