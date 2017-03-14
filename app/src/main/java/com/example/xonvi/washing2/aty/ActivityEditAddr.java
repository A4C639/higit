package com.example.xonvi.washing2.aty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.xonvi.washing2.Entity.AddrListResEntity;
import com.example.xonvi.washing2.Entity.MyAddr;
import com.example.xonvi.washing2.Entity.MyEvent;
import com.example.xonvi.washing2.Entity.NewAddrResEntity;
import com.example.xonvi.washing2.Entity.UpEvent;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.AddrListAdp;
import com.example.xonvi.washing2.adapter.EditAddrAdp;
import com.example.xonvi.washing2.adapter.TopRVAdapter;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.presenter.AddrPresenter;
import com.example.xonvi.washing2.presenter.NewAddrPresenter;
import com.example.xonvi.washing2.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/9.
 */

//管理收货地址的页面(Edit关键字不是很合适做为这个Activity的名字)
public class ActivityEditAddr extends BaseActivity implements EditAddrAdp.IUpdateCallBack {
    //通过startActivityForResult启动新增地址的activity使用的标记
    public final int NEW_ADDR = 0x1;
    //编辑地址列表的适配器
    private EditAddrAdp editAddrAdp;
    //管理地址的列表
    @BindView(R.id.lv_aty_addr_edit)ListView lv_aty_addr_edit;
    @OnClick({R.id.tv_aty_addr_edit_newaddr,R.id.iv_aty_edit_addr_back})
    public void click(View view){
        switch (view.getId()){
            //退出管理地址页面时
            case R.id.iv_aty_edit_addr_back:
                //返回时刷新ActivityAddress的页面 (通过事件总线向ActivityAddress页面发送消息 通知页面更新)
                EventBus.getDefault().post(new UpEvent());
                this.finish();
                break;
            //新增地址
            case R.id.tv_aty_addr_edit_newaddr:
                startActivityForResult(new Intent(this,ActivityNewAddr.class),NEW_ADDR);
                break;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_addr);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        initAddressList();
    }

    //获取收货人地址列表I
    private void initAddressList() {
        new AddrPresenter().getAddrList(MyApplication.getInstance().myUser.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<AddrListResEntity>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("addreatyaddress",e.toString());
                    }
                    @Override
                    public void onNext(AddrListResEntity entity) {
                        ToastUtil.toast(entity.getMsg());
                        Log.e("netx",entity.toString());
                        if(entity.getCode()==1){//已经填写地址
                            //显示订单列表
                             editAddrAdp = new EditAddrAdp(entity.getData());
                            editAddrAdp.setCallBack(ActivityEditAddr.this);
                            lv_aty_addr_edit.setAdapter(editAddrAdp);
                        }else {
                            ToastUtil.toast("还没有填写地址");
                        }
                    }
                });
    }

    //activitynewaddr result返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_ADDR &&resultCode==RESULT_OK){//如果新增地址成功
            //刷新收货地址列表
            initAddressList();

        }
//        else if(requestCode  == UPDATE_ADDR&&resultCode==RESULT_OK){
//            MyAddr newaddr = (MyAddr) data.getSerializableExtra("data");
//            List<MyAddr> list = editAddrAdp.getList();
//            list.set(newaddr.getPosition(), newaddr);
//            //更新列表
//            editAddrAdp.setList(list);
//        }
    }

    //地址列表中item中点击事件的回掉事件适配器EditAddrAdp中的点击回掉事件
    @Override
    public void updateCallBack(int pos) {

        //先跳转到修改地址的界面 将之前的地址信息传递过去
        //将地址在列表中的位置也传递过去
        MyAddr myAddr = editAddrAdp.getList().get(pos);
        myAddr.setPosition(pos);
        startActivity(new Intent(this,ActivityUpdateAddr.class).putExtra("addr",myAddr));
        Log.e("adp edit edit","tv_item_edit_addr_edit"+"--------"+myAddr.getId());


    }
//eventbus 总线事件(此处接受来自ActivityUpdateAddr修改完成后的事件)
    @Subscribe
    public void onEventMainThread(MyEvent event){
        Log.e("eventedit",event.event+"  ");
        switch (event.event){
            case MyEvent.UPDATE_ADDR_FINISH:
                initAddressList();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
