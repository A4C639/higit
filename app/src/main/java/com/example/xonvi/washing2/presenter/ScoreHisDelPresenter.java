package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.ScoreHisDelResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IScoreHisDel;

import rx.Observable;

/**
 * Created by xonvi on 2017/3/1.
 */

//删除兑换积分历史的代理
public class ScoreHisDelPresenter {
    public Observable<ScoreHisDelResEntity> delHis(int id){
        return NetManager.getServer(NetManager.LOCAL_URL).create(IScoreHisDel.class).delHis(id);
    }
}
