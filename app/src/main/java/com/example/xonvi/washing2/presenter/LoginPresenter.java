package com.example.xonvi.washing2.presenter;


import com.example.xonvi.washing2.Entity.LoginResEntity;
import com.example.xonvi.washing2.Entity.RegisterResEntity;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IUserLogin;


import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import retrofit2.Retrofit;
import rx.Observable;

import static com.example.xonvi.washing2.manager.NetManager.LOCAL_URL;

/**
 * Created by xonvi on 2017/1/4.
 */

//用户登陆的presenter帮助类
public class LoginPresenter {

    public Observable<LoginResEntity> doUserLogin(String username, String userpass)  {
        return  NetManager.getServer(LOCAL_URL).create(IUserLogin.class).userLogin(username, userpass);
    }
}
