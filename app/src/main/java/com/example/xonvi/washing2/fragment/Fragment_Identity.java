package com.example.xonvi.washing2.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.LoginResEntity;
import com.example.xonvi.washing2.Entity.MyEvent;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.aty.ActivityAddress;
import com.example.xonvi.washing2.aty.ActivityCharge;
import com.example.xonvi.washing2.aty.ActivityDiscount;
import com.example.xonvi.washing2.aty.ActivityIndex;
import com.example.xonvi.washing2.aty.ActivityLogin;
import com.example.xonvi.washing2.aty.ActivitySecurity;
import com.example.xonvi.washing2.aty.ActivityShop;
import com.example.xonvi.washing2.aty.userinfo.ActivityUserInfo;
import com.example.xonvi.washing2.presenter.LoginPresenter;
import com.example.xonvi.washing2.service.INewAddr;
import com.example.xonvi.washing2.util.SnakeBarUtil;
import com.example.xonvi.washing2.util.ToastUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2016/12/13.
 */

//用户个人信息
public class Fragment_Identity extends Fragment implements ActivityIndex.ISendToFrgIdentity {
    //仅浏览地址
    public static final int ADDRESS_SCAN = 0x110;
    //仅浏览优惠券
    public static final int DISCOUNT_PATTERN_SCAN =0x100;
    //root layout
    @BindView(R.id.ll_frg_identity_parent)LinearLayout ll_frg_identity_parent;
    //消费积分
    @BindView(R.id.tv_frg_score_value)TextView tv_frg_score_value;
    //用户余额
    @BindView(R.id.tv_frg_balance_value)TextView tv_frg_balance_value;
    //用户名
    @BindView(R.id.tv_frg_identity_login)TextView tv_frg_identity_login;
    //个人信息页的用户头像
    @BindView(R.id.sdv_frg_identity_uportrait)SimpleDraweeView sdv_frg_identity_uportrait;
    @OnClick({R.id.tv_frg_identity_login,R.id.sdv_frg_identity_uportrait,R.id.ll_frg_identity_address,R.id.ll_frg_identity_score,
    R.id.ll_frg_identity_security,R.id.tv_frg_identity_charge,R.id.ll_frg_identity_discount})
    public void click(View view){
        switch (view.getId()){
            //启动登陆界面
            case R.id.tv_frg_identity_login:
                if(MyApplication.getInstance().myUser==null){
                    startActivity(new Intent(getActivity(), ActivityLogin.class));
                }
                break;
            //点击用户头像(如果登陆状态显示用户个人信息)
            case R.id.sdv_frg_identity_uportrait:
                if(MyApplication.getInstance().myUser==null){
                    return;
                }
                startActivity(new Intent(getActivity(),ActivityUserInfo.class));

                break;
            //地址管理
            case R.id.ll_frg_identity_address:
                if(MyApplication.getInstance().myUser==null){
                    SnakeBarUtil.shortBar(ll_frg_identity_parent,"请先登录");
                    return;
                }
                Intent address = new Intent(getActivity(),ActivityAddress.class);
                address.putExtra("PATTERN",ADDRESS_SCAN);
                startActivity(address);
                break;
            //积分商城
            case R.id.ll_frg_identity_score:
                startActivity(new Intent(getActivity(), ActivityShop.class));
                break;
            //安全设置
            case R.id.ll_frg_identity_security:
                if(MyApplication.getInstance().myUser==null){
                    SnakeBarUtil.shortBar(ll_frg_identity_parent,"请先登录");
                    return;
                }
                startActivity(new Intent(getActivity(), ActivitySecurity.class));
                break;
            //优惠券
            case R.id.ll_frg_identity_discount:
                if(MyApplication.getInstance().myUser==null){
                    SnakeBarUtil.shortBar(ll_frg_identity_parent,"请先登录");
                    return;
                }
                Intent discount = new Intent(getActivity(),ActivityDiscount.class);
                discount.putExtra("PATTERN",DISCOUNT_PATTERN_SCAN);
                startActivity(discount);
                break;
            //充值
            case R.id.tv_frg_identity_charge:
                if(MyApplication.getInstance().myUser==null){
                    SnakeBarUtil.shortBar(ll_frg_identity_parent,"请先登录");
                    return;
                }
                startActivity(new Intent(getActivity(), ActivityCharge.class));
                break;
        }
    }





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_identity,null);
        ButterKnife.bind(this,view);

        ActivityIndex activityIndex = (ActivityIndex) getActivity();
        activityIndex.setiSendToFrgIdentity(this);

        initUserInfo();
        return view;
    }
    //初始化用户信息
    private void initUserInfo(){
        if(MyApplication.getInstance().myUser!=null){
          sdv_frg_identity_uportrait.setImageURI(MyApplication.getInstance().myUser.getPortrait());
            tv_frg_identity_login.setText(MyApplication.getInstance().myUser.getUsername());
            tv_frg_balance_value.setText(MyApplication.getInstance().myUser.getBalance()+"");
            tv_frg_score_value.setText(MyApplication.getInstance().myUser.getScore()+"");
        }else {//如果没有登陆
            sdv_frg_identity_uportrait.setImageURI("");
            tv_frg_identity_login.setText("立即登陆");
            tv_frg_balance_value.setText("0.00元");
            tv_frg_score_value.setText("0");
        }
    }

    //当fragment用户可见的时候

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
        }
    }


    //ActivityIndex的消息回掉接口
    //刷新页面内容(MyApplication中的内容不是去后台重新获取)
    @Override
    public void msgGet(MyEvent event) {
        Log.e("frgidentity","  frgidentity"+event.event);
        if(event.event==MyEvent.PORTRAIT_UPLOAD_SUCCESS){//修改头像成功
            MyApplication.getInstance().myUser.setPortrait(event.getPortrait());
        }
        initUserInfo();

    }

}
