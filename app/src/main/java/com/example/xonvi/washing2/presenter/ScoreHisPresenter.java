package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.ScoreThingResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IScoreHistory;

import rx.Observable;

/**
 * Created by xonvi on 2017/3/1.
 */

//请求积分兑换历史列表的代理
public class ScoreHisPresenter {
    public Observable<ScoreThingResEntity> scoreHistory(int uid){
        return NetManager.getServer(NetManager.LOCAL_URL).create(IScoreHistory.class).scoreHistory(uid);
    }
}
