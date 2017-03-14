package com.example.xonvi.washing2.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.xonvi.washing2.Entity.ChargeResEntity;
import com.example.xonvi.washing2.Entity.MyEvent;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.presenter.ChargePresenter;
import com.example.xonvi.washing2.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/15.
 */

//充值页面
public class ActivityCharge extends BaseActivity {

    @BindView(R.id.edt_aty_charge)EditText edt_aty_charge;


    @OnClick({R.id.tv_aty_charge_confirm,R.id.iv_aty_charge_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_charge_back:
                this.finish();
                break;
            //充值
            case R.id.tv_aty_charge_confirm:
                String s = edt_aty_charge.getText().toString();
                if(TextUtils.isEmpty(s)){
                    ToastUtil.toast("请输入正确的金额");
                    return;
                }
                int charge = Integer.parseInt(s);
                userCharge(charge);
                break;

        }
    }


    //用户充值
    private void userCharge(final int charge) {
        new ChargePresenter().doCharge(MyApplication.getInstance().myUser.getId(),charge)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ChargeResEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("chargeerror",e.toString());
                    }
                    @Override
                    public void onNext(ChargeResEntity entity) {
                        ToastUtil.toast(entity.getMsg());
                        if(entity.getCode()==1){//充值成功
                            //刷新framentidentity个人信息页的余额
                            int balance = MyApplication.getInstance().myUser.getBalance();
                            MyApplication.getInstance().myUser.setBalance(balance+charge);
                            EventBus.getDefault().post(new MyEvent(MyEvent.CHARGE_SUCCESS));
                            ActivityCharge.this.finish();

                        }

                    }
                });


    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);
        ButterKnife.bind(this);
    }
}
