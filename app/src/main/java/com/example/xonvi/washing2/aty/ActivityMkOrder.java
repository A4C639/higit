package com.example.xonvi.washing2.aty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.AddrListResEntity;
import com.example.xonvi.washing2.Entity.CartListEvent;
import com.example.xonvi.washing2.Entity.Discount;
import com.example.xonvi.washing2.Entity.MyAddr;
import com.example.xonvi.washing2.Entity.MyArticle;
import com.example.xonvi.washing2.Entity.MyEvent;
import com.example.xonvi.washing2.Entity.SubmitResEntity;
import com.example.xonvi.washing2.Entity.Thing;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.OrderRVAdp;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.presenter.AddrPresenter;
import com.example.xonvi.washing2.presenter.SubmitPresenter;
import com.example.xonvi.washing2.util.TimeUitl;
import com.example.xonvi.washing2.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/8.
 */

public class ActivityMkOrder extends BaseActivity implements OrderRVAdp.MyClickListener, View.OnClickListener, PopupWindow.OnDismissListener {
    //选取优惠券
    public static final int DISCOUNT_PATTERN_PICK = 0x101;
    //下订单时发现后台没有地址 那么跳转到地址填写的activity
    public static final int NEW_ADDR_FIRST = 0x11;
    public final int PICK_ADDRESS = 0x12;
    //保存需要确认付款的订单对象
    private MyArticle myArticle;
    //获取地址的代理
    private AddrPresenter addrPresenter;
    //提交订单的代理
    private SubmitPresenter submitPresenter;
    //订单详情的弹出窗
    private PopupWindow popupWindow;
    private View popview;
    //支付方式选择
    private RelativeLayout rl_bank;
    //优惠券
    private RelativeLayout rl_discount;
    //优惠券描述信息
    private TextView tv_desc;
    //确认付款
    private TextView pay;
    //关闭弹窗
    private ImageView popup_close;
    //实际支付总金额
    private TextView popup_total;
    //父布局
    @BindView(R.id.ll_aty_mkorder_parent)LinearLayout ll_aty_mkorder_parent;

    //确认订单列表的适配器
    private OrderRVAdp orderRVAdp;
    //订单确认列表的总价
    @BindView(R.id.tv_aty_mkorder_total)TextView tv_aty_mkorder_total;
    //订单确认的列表recyclerview
    @BindView(R.id.rv_aty_mkorder)RecyclerView rv_aty_mkorder;
    @OnClick({R.id.iv_aty_mkorder_back,R.id.tv_aty_mkorder_submit})
    public void click(View v){
        switch (v.getId()){
            case R.id.iv_aty_mkorder_back:
                this.finish();
                break;
            //提交订单(这里就会向后台提交订单)
            case R.id.tv_aty_mkorder_submit:
                //弹窗提示订单详细信息
                popWindow();
                break;
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mkorder);
        ButterKnife.bind(this);
        addrPresenter = new AddrPresenter();
        submitPresenter = new SubmitPresenter();
        address();
    }

    //初始化订单列表---------------------------------------------------------------------------------
    private void initOrderList(MyAddr myAddr) {


        List<Thing> cartlist = (List<Thing>) getIntent().getExtras().getSerializable("cartlist");
        //计算总价
        int total = 0;
        for(Thing thing :cartlist){
            total+=(thing.getPrice()*thing.getCount());
        }
        tv_aty_mkorder_total.setText(total+"");

        //如果成功显示订单信息那么构造一个订单对象
        if(cartlist.size()>0&&myAddr!=null){
            myArticle = new MyArticle();
            //获取总价
            myArticle.setTotal(tv_aty_mkorder_total.getText().toString());
            //获取当前用户的id
            myArticle.setUid(MyApplication.getInstance().myUser.getId());
            myArticle.setAddress(myAddr);
            myArticle.setThingList(new Gson().toJson(cartlist));
            myArticle.setTime(TimeUitl.getTime());
            myArticle.setTotal(total+"");

        }else {
            ToastUtil.toast("请检查购物车列表和收货地址");
            return;
        }

        Log.e("cartlist",cartlist.size()+" ");
         orderRVAdp = new OrderRVAdp(cartlist,myAddr);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_aty_mkorder.setLayoutManager(linearLayoutManager);
        orderRVAdp.setMyClickListener(this);
        rv_aty_mkorder.setAdapter(orderRVAdp);

    }

    //获取收货人地址列表---------------------------------------------------------------------------------
    private void address() {
        addrPresenter.getAddrList(MyApplication.getInstance().myUser.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<AddrListResEntity>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("eaddr"," "+e.toString());
                    }
                    @Override
                    public void onNext(AddrListResEntity entity) {
                        Log.e("nulladdrlist",(entity==null)+" "+entity.toString());
                        if(entity.getCode()==1){//已经填写了地址
                           ToastUtil.toast("使用了默认地址");
                            initOrderList(entity.getData().get(0));
                        }else {//没有地址
                            ToastUtil.toast("请填写地址");
                            //启动新增地址的activity
                            startActivityForResult(new Intent(ActivityMkOrder.this,ActivityNewAddr.class),NEW_ADDR_FIRST);
                        }
                    }
                });

    }


    //提交订单---------------------------------------------------------------------------------
    private void submitArticle(final MyArticle myArticle) {
        //先判断当前用户余额是否足够支付订单
                if(!balanceCheck(myArticle)){
                    return;
                }
        //显示支付详情

        Log.e("ding单提交","提交了111111111");
        submitPresenter.doSubmitList(myArticle)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<SubmitResEntity>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("下单异常",e.toString());
                    }
                    @Override
                    public void onNext(SubmitResEntity entity) {
                        if(entity.getCode()==1){
                            ToastUtil.toast(entity.getMsg());
                            EventBus.getDefault().post(new CartListEvent(CartListEvent.SUBMIT_COMPLETED));
                            //下单成功后扣除余额
                            int balance = MyApplication.getInstance().myUser.getBalance();
                            int total = Integer.parseInt(myArticle.getTotal());
                            MyApplication.getInstance().myUser.setBalance(balance-total);
                            //增加对应的积分(消费一块钱增加一积分)
                            int score = MyApplication.getInstance().myUser.getScore();
                            MyApplication.getInstance().myUser.setScore(score+total);

                            ActivityMkOrder.this.finish();
                        }
                    }
                });
    }

    //余额检查---------------------------------------------------------------------------------
    private boolean balanceCheck(MyArticle myArticle) {
        //订单总额
        int total = Integer.parseInt(myArticle.getTotal());
        int balance = MyApplication.getInstance().myUser.getBalance();
        if(total>=balance){//订单总额超过当前余额
            ToastUtil.toast("余额不足");
            return false;
        }

        return true;

    }

    //填写地址选取返回结果---------------------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==NEW_ADDR_FIRST&&resultCode==RESULT_OK){
            address();
        }
        else if(requestCode==NEW_ADDR_FIRST&&resultCode==RESULT_CANCELED){
            //如果本来就没有填写地址，然后跳转到新增地址的接面时又取消了新增地址，那么这个页面也一并finish、
            this.finish();
        }
        else if(requestCode==PICK_ADDRESS&&resultCode==RESULT_OK){
            orderRVAdp.setMyAddr((MyAddr) data.getSerializableExtra("address"));
        }else if(requestCode==DISCOUNT_PATTERN_PICK&&resultCode==RESULT_OK){

            Discount discount = (Discount) data.getSerializableExtra("discount");
            tv_desc.setText(discount.getDesc());

            popup_total.setText(Integer.parseInt(myArticle.getTotal())-discount.getDiscount()+"");
            ToastUtil.toast("ssss"+discount.getDesc());
        }
    }

    //确认订单的header 点击事件回掉---------------------------------------------------------------------------------
    @Override
    public void onHeaderClick(int pos, View view) {
        Log.e("headerclick",pos+"  ");
        startActivityForResult(new Intent(ActivityMkOrder.this,ActivityAddress.class),PICK_ADDRESS);

    }

    //订单详情------------------------------------------------------------------------------------------------------

    private void popWindow() {
        if(popupWindow==null||popupWindow==null) {
            popview = getLayoutInflater().inflate(R.layout.popup_aty_mkorder_pay_detail, null);
            popupWindow = new PopupWindow(popview, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,true);
        }
        Log.e("ddd","popoopopo");



        rl_bank = (RelativeLayout) popview.findViewById(R.id.rl_popup_aty_mkorder_bank);
        rl_discount = (RelativeLayout) popview.findViewById(R.id.rl_popup_aty_mkorder_discount);
        pay = (TextView) popview.findViewById(R.id.tv_popup_aty_mkorder_pay);
        popup_close = (ImageView)popview.findViewById(R.id.iv_aty_popup_mkorder_close);
        popup_total = (TextView) popview.findViewById(R.id.tv_popup_aty_mkorder_total);
        tv_desc = (TextView)popview.findViewById(R.id.tv_popup_aty_mkorder_discount_desc);
        rl_bank.setOnClickListener(this);
        rl_discount.setOnClickListener(this);
        pay.setOnClickListener(this);
        popup_close.setOnClickListener(this);
        popupWindow.setOnDismissListener(this);
        popup_total.setText(myArticle.getTotal());


        popupWindow.setTouchable(true);
        popupWindow.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//防止软件盘被弹窗遮挡
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),(Bitmap) null));
        //showAsDropDown是按下显
//        popupWindow.showAsDropDown(fl_aty_thing_cart,0,-900, Gravity.TOP);
        //如果要定位精确显示popwindow那就使用showAtLocation
        popupWindow.showAtLocation(ll_aty_mkorder_parent, Gravity.CENTER,0,0);
        backgroundAlpha(0.4f);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * 设置添加屏幕的背景透明度-------------------------------------------------------------------------
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }



    //付款详情弹出窗的点击事件-------------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //弹出窗关闭
            case R.id.iv_aty_popup_mkorder_close:
                popupWindow.dismiss();
                break;
            //确认支付
            case R.id.tv_popup_aty_mkorder_pay:
                //提交订单
                submitArticle(myArticle);
                break;
            //选择付款方式
            case R.id.rl_popup_aty_mkorder_bank:
                break;
            //优惠券使用
            case R.id.rl_popup_aty_mkorder_discount:
                Intent discount = new Intent(this,ActivityDiscount.class);
                discount.putExtra("PATTERN",DISCOUNT_PATTERN_PICK);
                startActivityForResult(discount,DISCOUNT_PATTERN_PICK);
                break;
        }

    }

    //当popwindow被关闭时--------------------------------------------------------------------------------
    @Override
    public void onDismiss() {
        backgroundAlpha(1f);

    }
}
