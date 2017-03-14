package com.example.xonvi.washing2.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.xonvi.washing2.Entity.RegisterResEntity;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.presenter.RegPresenter;
import com.example.xonvi.washing2.util.RegisterCheckUtil;
import com.example.xonvi.washing2.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/4.
 */

public class ActivityReg extends BaseActivity {


    @BindView(R.id.edt_aty_reg_username)EditText edt_aty_reg_username;
    @BindView(R.id.edt_aty_reg_userpass)EditText edt_aty_reg_userpass;
    @BindView(R.id.edt_aty_reg_again)EditText edt_aty_reg_again;


    @OnClick({R.id.tv_aty_reg_submit,R.id.iv_aty_reg_back})
    public void click(View view){
        switch (view.getId()){
            //提交用户注册数据
            case R.id.tv_aty_reg_submit:
                submitReg();
                break;
            case R.id.iv_aty_reg_back:
                this.finish();
                break;
        }
    }

    //提价前台验证
    private void submitReg() {

        String username = edt_aty_reg_username.getText().toString();
        String userpass = edt_aty_reg_userpass.getText().toString();
        String again = edt_aty_reg_again.getText().toString();
        //简单的前台验证
        boolean lengthCheck = RegisterCheckUtil.lengthCheck(userpass, username);
        boolean passCheck = RegisterCheckUtil.passCheck(userpass, again);
        if(lengthCheck&&passCheck){
            //如果通过前台验证 那么讲注册数据提交后台验证
            userReg(username,userpass);

        }


    }

    //提交后台验证
    private void userReg(String username,String userpass) {
        new RegPresenter().doUserReg(username,userpass)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<RegisterResEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        ToastUtil.toast("注册出现异常");
                        Log.e("regerr",e.toString());
                    }

                    @Override
                    public void onNext(RegisterResEntity res) {
                        ToastUtil.toast(res.getMsg());

                        if(res.getCode()==1){
                            ActivityReg.this.finish();
                        }

                    }
                });


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);




    }
}
