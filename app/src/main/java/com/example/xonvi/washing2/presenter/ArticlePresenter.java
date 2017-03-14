package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.ArticleResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IArticleList;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/10.
 */

//订单列表的presenter

public class ArticlePresenter {

    public Observable<ArticleResEntity> getArticleList(int uid){
        return NetManager.getServer(NetManager.LOCAL_URL).create(IArticleList.class).articleList(uid);
    }
}
