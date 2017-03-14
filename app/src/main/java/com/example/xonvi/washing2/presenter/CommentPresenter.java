package com.example.xonvi.washing2.presenter;

import com.example.xonvi.washing2.Entity.CommentResEntity;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.manager.NetManager;
import com.example.xonvi.washing2.service.IComment;
import com.example.xonvi.washing2.util.TimeUitl;

import rx.Observable;

/**
 * Created by xonvi on 2017/2/14.
 */

//用户评论的presenter
public class CommentPresenter {

    public Observable<CommentResEntity> doComment(String comment){
        String uname = MyApplication.getInstance().myUser.getUsername();
        String portrait = MyApplication.getInstance().myUser.getPortrait();
        String time = TimeUitl.getTime();
        return NetManager.getServer(NetManager.LOCAL_URL).create(IComment.class).comment(uname,portrait,comment,time);
    }
}
