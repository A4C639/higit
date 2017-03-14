package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.ScoreResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IScore;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/27.
 */


//积分商城购买的presenter
public class ScorePresenter {
    public Observable<ScoreResEntity> useScore(int uid,int sid,String icon,String description,int score){
        return NetManager.getServer(NetManager.LOCAL_URL).create(IScore.class).score(uid,sid,icon,description,score);
    }

}
