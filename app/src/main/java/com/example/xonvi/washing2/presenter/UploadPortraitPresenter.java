package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.UploadPortraitResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IUploadPortrait;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/20.
 */

//用户设置头像上传的presenter
public class UploadPortraitPresenter {

    public Observable<UploadPortraitResEntity> uploadPortrait(int uid, RequestBody file){


        return NetManager.getServer(NetManager.LOCAL_URL).create(IUploadPortrait.class).uploadPortrait(uid,file);
    }
}
