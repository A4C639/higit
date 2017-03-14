package com.example.xonvi.washing2.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.xonvi.washing2.Entity.NewAddrResEntity;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.presenter.NewAddrPresenter;
import com.example.xonvi.washing2.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/9.
 */

public class ActivityNewAddr extends BaseActivity{
    private NewAddrPresenter newAddrPresenter;
    @BindView(R.id.edt_aty_new_addr_receiver)EditText edt_aty_new_addr_receiver;
    @BindView(R.id.edt_aty_new_addr_phone)EditText edt_aty_new_addr_phone;
    @BindView(R.id.edt_aty_new_addr_address)EditText edt_aty_new_addr_address;

    @OnClick({R.id.iv_aty_new_addr_back,R.id.tv_aty_new_addr_save})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_new_addr_back:
                setResult(RESULT_CANCELED);
                this.finish();
                break;
            case R.id.tv_aty_new_addr_save:
                Log.e("save","fffffff");
               String receiver = edt_aty_new_addr_receiver.getText().toString();
                String phone = edt_aty_new_addr_phone.getText().toString();
                String address = edt_aty_new_addr_address.getText().toString();

                if(TextUtils.isEmpty(receiver)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(address)){
                    ToastUtil.toast("请检查未填写项,不能为空");
                    return;
                }

                newAddr(MyApplication.getInstance().myUser.getId(),receiver,phone,address);
                break;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_addr);
        newAddrPresenter = new NewAddrPresenter();
        ButterKnife.bind(this);
    }


    //新增地址
    private void newAddr(int uid,String name,String phone,String address) {
        newAddrPresenter.doNewAddr(uid,name, phone, address)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<NewAddrResEntity>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("editaddree",e.toString());
                    }
                    @Override
                    public void onNext(NewAddrResEntity entity) {
                        if (entity.getCode()==1){//新增地址成功
                            ToastUtil.toast(entity.getMsg());
                            //告诉ActivityEdit新增成功了
                            setResult(RESULT_OK);
                            ActivityNewAddr.this.finish();
                        }else if(entity.getCode()==1){
                            ToastUtil.toast(entity.getMsg());
                            setResult(RESULT_OK);
                            ActivityNewAddr.this.finish();
                        }
                    }
                });
    }
}
