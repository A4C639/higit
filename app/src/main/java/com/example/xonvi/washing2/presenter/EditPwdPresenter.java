package com.example.xonvi.washing2.presenter;

import android.animation.ObjectAnimator;

import com.example.xonvi.washing2.Entity.EditPwdResEntity;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IEditPwd;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/19.
 */

//修改密码的presenter
public class EditPwdPresenter {

    public Observable<EditPwdResEntity> doEditPwd(String oldpass,String newpass){
        int uid = MyApplication.getInstance().myUser.getId();
        return NetManager.getServer(NetManager.LOCAL_URL).create(IEditPwd.class).editPwd(uid,oldpass,newpass);
    }
}
