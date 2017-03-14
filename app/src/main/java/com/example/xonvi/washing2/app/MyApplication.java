package com.example.xonvi.washing2.app;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.example.xonvi.washing2.Entity.MyUser;
import com.example.xonvi.washing2.presenter.LoginPresenter;
import com.example.xonvi.washing2.util.ToastUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by xonvi on 2016/12/14.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //toast工具类初始化
        ToastUtil.initToast(this);
        //Fresco
        Fresco.initialize(this);



    }





    private static final MyApplication  singleton = new MyApplication();

    public static MyApplication getInstance(){
        return singleton;
    }


    //保存用户状态
    public MyUser myUser;


}
