package com.example.xonvi.washing2.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.xonvi.washing2.Entity.EditPwdResEntity;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.presenter.EditPwdPresenter;
import com.example.xonvi.washing2.util.RegisterCheckUtil;
import com.example.xonvi.washing2.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/19.
 */

//修改密码
public class ActivityEditPwd extends BaseActivity {

    @BindView(R.id.edt_aty_editpwd_again)EditText edt_aty_editpwd_again;
    @BindView(R.id.edt_aty_editpwd_newpass)EditText edt_aty_editpwd_newpass;
    @BindView(R.id.edt_aty_editpwd_oldpass)EditText edt_aty_editpwd_oldpass;

    @OnClick({R.id.iv_aty_editpwd_back,R.id.edt_aty_editpwd_oldpass,R.id.edt_aty_editpwd_newpass,
            R.id.edt_aty_editpwd_again,R.id.tv_aty_editpwd_submit})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_editpwd_back:
                this.finish();
                break;
            //取人修改密码
            case R.id.tv_aty_editpwd_submit:
                String oldpass = edt_aty_editpwd_oldpass.getText().toString();
                String newpass = edt_aty_editpwd_newpass.getText().toString();
                String again = edt_aty_editpwd_again.getText().toString();
                boolean passCheck = RegisterCheckUtil.passCheck(newpass,
                        again);

                if(passCheck){
                    confirmEditPwd(oldpass,again);
                }

                break;
        }
    }


    //确认修改密码
    private void confirmEditPwd(String oldpass,String again) {
        new EditPwdPresenter().doEditPwd(oldpass,again)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<EditPwdResEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("edtpwderror",e.toString());
                    }

                    @Override
                    public void onNext(EditPwdResEntity entity) {
                        ToastUtil.toast(entity.getMsg());
                        if(entity.getCode()==1){

                            ActivityEditPwd.this.finish();
                            openActivity(ActivityLogin.class);
                        }
                    }
                });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edtpwd);
        ButterKnife.bind(this);
    }
}
