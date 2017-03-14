package com.example.xonvi.washing2.aty;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.xonvi.washing2.Entity.LoginResEntity;
import com.example.xonvi.washing2.Entity.MyEvent;
import com.example.xonvi.washing2.Entity.RegisterResEntity;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.presenter.LoginPresenter;
import com.example.xonvi.washing2.util.SnakeBarUtil;
import com.example.xonvi.washing2.util.SoftInputUtil;
import com.example.xonvi.washing2.util.ToastUtil;


import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/3.
 */

//登录页面
public class ActivityLogin extends BaseActivity {


    @BindView(R.id.fl_aty_login_parent)FrameLayout fl_aty_login_parent;
    @BindView(R.id.fl_loading_anima_login)FrameLayout fl_loading_anima_login;
    @BindView(R.id.edt_aty_login_username)EditText edt_aty_login_username;
    @BindView(R.id.edt_aty_login_userpass)EditText edt_aty_login_userpass;
    @OnClick({R.id.iv_aty_login_back,R.id.tv_aty_login,R.id.tv_aty_login_reg})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_login_back:
                ActivityLogin.this.finish();
                break;
            //用户登陆
            case R.id.tv_aty_login:
                //隐藏虚拟键盘
                SoftInputUtil.hiddenSoftInput(edt_aty_login_username,this);
                //获取用户名密码
                String username = edt_aty_login_username.getText().toString();
                String userpass = edt_aty_login_userpass.getText().toString();
                Log.e("checklog",username+" "+userpass);
                //用户密码前台验证
                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(userpass)){
                    SnakeBarUtil.shortBar(fl_aty_login_parent,"用户名或者密码不能为空");
                    return;
                }
                userLogin(username,userpass);

                break;
            //跳转用户注册页面
            case R.id.tv_aty_login_reg:
                openActivity(ActivityReg.class);
                break;
        }
    }

    //用户登陆
    private void userLogin(String username,String userpass) {
        //显示加载动画
        fl_loading_anima_login.setVisibility(View.VISIBLE);
        //保存登陆账号密码
        saveLogin(username,userpass);

        //提交后台登陆验证
        new LoginPresenter().doUserLogin(username,userpass)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<LoginResEntity>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.toast("网络异常"+e.toString());
                        Log.e("logerror",e.toString());
                        fl_loading_anima_login.setVisibility(View.GONE);

                    }
                    @Override
                    public void onNext(LoginResEntity res) {
                        SnakeBarUtil.shortBar(fl_aty_login_parent,"用户名或者密码错误");
                        Log.e("loginres",res.toString());
                        if(res.getCode()==1){

                            //登陆成功就将用户信息保存
                            MyApplication.getInstance().myUser=res.getData();

                            Log.e("用户信息:",res.getData().toString());
                            //通过eventbus像主界面的activityindex发送消息
                            EventBus.getDefault().post(new MyEvent(MyEvent.LOGIN));
                            ActivityLogin.this.finish();
                        }
                        fl_loading_anima_login.setVisibility(View.GONE);
                    }
                });


    }

    //保存上次登陆信息
    private void saveLogin(String username,String userpass) {
        //保存用户名密码登陆
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        String string = sp.getString("username", "default");
//        Log.e("sp---",(sp==null)+" ");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username",username);
            editor.putString("userpass",userpass);
            editor.commit();

    }

    //记忆上次登陆
    private void remeberLogin() {
            SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
            String savedname = login.getString("username", "");
            String savedpass = login.getString("userpass", "");

            edt_aty_login_username.setText(savedname);
            edt_aty_login_userpass.setText(savedpass);
            Log.e("loginrember",savedname+"   "+savedpass);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //记忆上一次登陆
        remeberLogin();


    }


}
