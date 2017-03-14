package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.Thing;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IAccessoryList;
import com.example.xonvi.washing2.service.IBottomList;
import com.example.xonvi.washing2.service.IShoesList;
import com.example.xonvi.washing2.service.ITopList;

import java.util.List;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/5.
 */

//请求物品列表的presenter
public class ThingsListPresenter {
    public Observable<List<Thing>> getTopList(){
        return NetManager.getServer(NetManager.LOCAL_URL).create(ITopList.class).topList();
    }
    public Observable<List<Thing>> getShoesList(){
        return NetManager.getServer(NetManager.LOCAL_URL).create(IShoesList.class).shoesList();
    }
    public Observable<List<Thing>> getBottomList(){
        return NetManager.getServer(NetManager.LOCAL_URL).create(IBottomList.class).bottomList();
    }
    public Observable<List<Thing>> getAccessoryList(){
        return NetManager.getServer(NetManager.LOCAL_URL).create(IAccessoryList.class).accessoryList();
    }
}
