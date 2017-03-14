package com.example.xonvi.washing2.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.xonvi.washing2.Entity.ChatMsg;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.FeedBackRVAdp;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xonvi on 2017/2/17.
 */

//意见反馈
public class ActivityFeedback extends BaseActivity {
    private FeedBackRVAdp feedBackRVAdp;
    //反馈聊天列表
    @BindView(R.id.rv_aty_feedback)RecyclerView rv_aty_feedback;
    //输入反馈信息框
    @BindView(R.id.edt_aty_feedback)EditText edt_aty_feedback;
    @OnClick({R.id.iv_aty_feedback_send,R.id.iv_aty_feedback_back})
    public void click(View view){
        switch (view.getId()){
            //发送反馈内容
            case R.id.iv_aty_feedback_send:
                String msg = edt_aty_feedback.getText().toString();
                if(TextUtils.isEmpty(msg)){
                    ToastUtil.toast("消息不能为空");
                    return;
                }
                sendMsg(msg);
                break;
            case R.id.iv_aty_feedback_back:
                this.finish();
                break;
        }
    }

    //发送消息
    private void sendMsg(String msg) {
        String portrait = MyApplication.getInstance().myUser.getPortrait();
        feedBackRVAdp.setMsgList(new ChatMsg(msg,portrait));
        edt_aty_feedback.setText("");

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        //初始化反馈列表
        initMsgList();
    }

    //初始化消息列表
    private void initMsgList() {

        feedBackRVAdp = new FeedBackRVAdp();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        //不要忘记设置布局管理器
        rv_aty_feedback.setLayoutManager(manager);
        rv_aty_feedback.setAdapter(feedBackRVAdp);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
