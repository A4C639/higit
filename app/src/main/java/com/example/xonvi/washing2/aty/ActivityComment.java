package com.example.xonvi.washing2.aty;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.CommentResEntity;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.presenter.CommentPresenter;
import com.example.xonvi.washing2.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/14.
 */

//评论
public class ActivityComment extends BaseActivity {
    @BindView(R.id.iv_aty_comment_back)ImageView iv_aty_comment_back;
    @BindView(R.id.edt_aty_comment)EditText edt_aty_comment;
    @BindView(R.id.tv_aty_comment_send)TextView tv_aty_comment_send;
    @OnClick({R.id.tv_aty_comment_send,R.id.iv_aty_comment_back})
    public void click(View view){
        switch (view.getId()){
            //发送评论
            case R.id.tv_aty_comment_send:
                Log.e("comment","commment");
                comment(edt_aty_comment.getText().toString());
                break;
            case R.id.iv_aty_comment_back:
                ActivityComment.this.finish();
                break;
        }
    }

    //评论
    private void comment(String comment) {

        new CommentPresenter().doComment(comment)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<CommentResEntity>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("commenterror",e.toString());
                    }

                    @Override
                    public void onNext(CommentResEntity entity) {

                        ToastUtil.toast(entity.getMsg());
                        if(entity.getCode()==1){
                            ActivityComment.this.finish();
                        }

                    }
                });


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        setInputListener();
    }

    //设置textWatcher
    private void setInputListener() {
        tv_aty_comment_send.setEnabled(false);
        edt_aty_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tv_aty_comment_send.setTextColor(Color.parseColor("white"));
                tv_aty_comment_send.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    tv_aty_comment_send.setEnabled(true);
                }else {
                    tv_aty_comment_send.setEnabled(false);
                    tv_aty_comment_send.setTextColor(Color.parseColor("#c0c0c0"));

                }

            }
        });

    }


}
