package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.SkillResEntity;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/18.
 */

//洗衣小技巧列表的业务接口
public interface ISkill {

    @POST("Wash/SkillList")
    Observable<SkillResEntity> skillList();
}
