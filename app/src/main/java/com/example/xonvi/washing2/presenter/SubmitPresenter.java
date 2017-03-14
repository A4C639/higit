package com.example.xonvi.washing2.presenter;

import android.util.Log;

import com.example.xonvi.washing2.Entity.MyAddr;
import com.example.xonvi.washing2.Entity.MyArticle;
import com.example.xonvi.washing2.Entity.SubmitResEntity;
import com.example.xonvi.washing2.Entity.Thing;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.ISubmitList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/9.
 */

//提交订单的presenter

public class SubmitPresenter {
    public Observable<SubmitResEntity> doSubmitList(MyArticle myArticle){
        int uid = myArticle.getUid();
        MyAddr myAddr = myArticle.getAddress();
        String total = myArticle.getTotal();
        String time = myArticle.getTime();


        return NetManager.getServer(NetManager.LOCAL_URL).create(ISubmitList.class).submitList(uid,myAddr.getName(),myAddr.getPhone(),
                myAddr.getAddress(),myArticle.getThingList(),total,time);
    }
}
