package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.DelArticleResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IDelArticle;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/14.
 */


//删除订单的presenter
public class DelArticlePresenter {

    public Observable<DelArticleResEntity> doDelArticle(int id){
        return NetManager.getServer(NetManager.LOCAL_URL).create(IDelArticle.class).delArticle(id);
    }
}
