package com.example.xonvi.washing2.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.xonvi.washing2.Entity.MyAddr;
import com.example.xonvi.washing2.Entity.MyEvent;
import com.example.xonvi.washing2.Entity.UpdateAddrResEntity;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.presenter.UpdateAddrPresenter;
import com.example.xonvi.washing2.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/19.
 */
//修改地址的页面
public class ActivityUpdateAddr extends BaseActivity {

    //保存从地址列表传递过来的待修改的地址
    private MyAddr myAddr;
    //详细地址
    @BindView(R.id.edt_aty_updateaddr_address)EditText edt_aty_updateaddr_address;
    //电话
    @BindView(R.id.edt_aty_updateaddr_phone)EditText edt_aty_updateaddr_phone;
    //收货人
    @BindView(R.id.edt_aty_updateaddr_receiver)EditText edt_aty_updateaddr_receiver;

    @OnClick({R.id.iv_aty_updateaddr_back,R.id.tv_aty_updateaddr_save})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_updateaddr_back:
                this.finish();
                break;
            //确定更改
            case R.id.tv_aty_updateaddr_save:
                String addr = edt_aty_updateaddr_address.getText().toString();
                String name = edt_aty_updateaddr_receiver.getText().toString();
                String phone = edt_aty_updateaddr_phone.getText().toString();
                if(TextUtils.isEmpty(addr)||TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)){
                    ToastUtil.toast("请检查为空的栏目，不允许为空");
                    return;
                }
                //提交修改
                updateMyaddr(myAddr.getId(),addr,name,phone);

                break;
        }
    }


    //确认修改地址
    private void updateMyaddr(int aid, final String addr, final String name, final String phone) {
        new UpdateAddrPresenter()
                .doUpdateAddr(aid,addr,name,phone)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<UpdateAddrResEntity>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("updateaddrerror",e.toString());
                    }
                    @Override
                    public void onNext(UpdateAddrResEntity entity) {
                        ToastUtil.toast(entity.getMsg());
                        if(entity.getCode()==1){//修改地址成功
                            //告诉地址管理列表的activityeditaddr更新
                            EventBus.getDefault().post(new MyEvent(MyEvent.UPDATE_ADDR_FINISH));
                            ActivityUpdateAddr.this.finish();
                        }

                    }
                });


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateaddr);
        ButterKnife.bind(this);

        initUpdatePage();
    }


    //初始化 修改收货地址列表
    private void initUpdatePage() {
        myAddr = (MyAddr) getIntent().getSerializableExtra("addr");
        edt_aty_updateaddr_address.setText(myAddr.getAddress());
        edt_aty_updateaddr_phone.setText(myAddr.getPhone());
        edt_aty_updateaddr_receiver.setText(myAddr.getName());
    }
}
