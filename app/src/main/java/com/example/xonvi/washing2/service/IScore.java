package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.ScoreResEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/27.
 */


//积分商城的购买物品
/**
 *参数 uid 当前用户的id
 *参数 score 消耗的积分
 * 参数 description 积分物品的描述
 * 参数 icon 积分物品的图像
 * 参数 sid 积分物品的id
 */

public interface IScore {
    @FormUrlEncoded
    @POST("Wash/Score")
    Observable<ScoreResEntity> score(@Field("uid") int uid,
                                     @Field("sid") int sid,
                                     @Field("icon")String icon,
                                     @Field("desc")String description,
                                     @Field("score") int score

    );
}
