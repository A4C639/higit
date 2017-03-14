package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.SkillEntity;
import com.example.xonvi.washing2.Entity.SkillResEntity;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.ISkill;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/18.
 */

//洗衣小技巧列表的presenter
public class SkillPresenter {

    public Observable<SkillResEntity> getSkillList(){
        return NetManager.getServer(NetManager.LOCAL_URL).create(ISkill.class).skillList();
    }
}
