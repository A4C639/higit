package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.CmtListResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.ICommentList;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/14.
 */

//请求评论列表的presenter
public class CmtListPresenter {
    public Observable<CmtListResEntity> getCmtList(){
        return NetManager.getServer(NetManager.LOCAL_URL).create(ICommentList.class).commentList();
    }
}
