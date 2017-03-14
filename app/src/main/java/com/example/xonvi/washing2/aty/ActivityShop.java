package com.example.xonvi.washing2.aty;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.MyEvent;
import com.example.xonvi.washing2.Entity.ScoreResEntity;
import com.example.xonvi.washing2.Entity.ShopItem;
import com.example.xonvi.washing2.Entity.ShopItemEntity;
import com.example.xonvi.washing2.Entity.Thing;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.ShopRVAdp;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.presenter.ScorePresenter;
import com.example.xonvi.washing2.presenter.ShopPresenter;
import com.example.xonvi.washing2.util.SnakeBarUtil;
import com.example.xonvi.washing2.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/2/15.
 */

//积分商城
public class ActivityShop extends BaseActivity implements ShopRVAdp.ShopItemClick, PopupWindow.OnDismissListener, View.OnClickListener {

    @BindView(R.id.fl_loading_anima_aty_score_shop)FrameLayout fl_loading_anima_aty_score_shop;
    //-----------------------------------------------加载动画------------------------------------------------

    private TextView tv_aty_popup_score_history;
    private PopupWindow popupWindow2;
    private View popView2;
    // --------------------------------------------------------------------popwindow2显示兑换历史记录-----------------------------------------------------

    //关闭详情弹窗
    private ImageView iv_aty_popup_shopitem_close;
    //图片
    private SimpleDraweeView sdv_aty_popup_shopitem_pic;
    //弹出积分商品详情 购买按钮
    private TextView tv_aty_popup_shopitem_buy;
    //积分商城列表项
    private List<ShopItem> shopItemList;
    //父布局
    @BindView(R.id.fl_aty_shop_parent)FrameLayout fl_aty_shop_parent;
    @BindView(R.id.iv_aty_shop_more)ImageView iv_aty_shop_more;
    //popupwindow1
    private PopupWindow popupWindow;
    private View popview;
   // --------------------------------------------------------------------popwindow1显示商品详情-----------------------------------------------------
    //商品列表
    @BindView(R.id.rv_aty_shop_list)RecyclerView rv_aty_shop_list;
    @OnClick({R.id.iv_aty_shop_back,R.id.iv_aty_shop_more})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_shop_back:
                this.finish();
                break;
            case R.id.iv_aty_shop_more:
                popWindow2();
                break;
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);

        initShopList();
    }


    //初始化商城列表
    private void initShopList() {
//开始加载动画
        fl_loading_anima_aty_score_shop.setVisibility(View.VISIBLE);
        new ShopPresenter().doShopList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ShopItemEntity>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("shoperror",e.toString());
                        fl_loading_anima_aty_score_shop.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(ShopItemEntity entity) {
                        //将积分商城的项保存
                       shopItemList = entity.getData();
                        ToastUtil.toast(entity.getMsg());
                        if(entity.getCode()==1){
                            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                            rv_aty_shop_list.setLayoutManager(manager);
                            ShopRVAdp shopRVAdp = new ShopRVAdp(shopItemList);
                            shopRVAdp.setItemClick(ActivityShop.this);
                            rv_aty_shop_list.setAdapter(shopRVAdp);
                            //停止动画加载
                            fl_loading_anima_aty_score_shop.setVisibility(View.GONE);
                        }
                    }
                });




    }


    //积分商城物品项的点击事件-----------------------------------------------------------------------------------------------------------------------------
    @Override
    public void shopItemClick(int pos, View view) {

        popWindow(pos);
    }


    //弹出窗商品详情------popwindow1-----------------------------------------------------------------------------------------------------------------------------
    //弹出窗
    private void popWindow(int postion) {
        if(popupWindow==null||popupWindow==null) {
            popview = getLayoutInflater().inflate(R.layout.aty_shop_popup_window, null);
            popupWindow = new PopupWindow(popview, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,true);
        }
        Log.e("ddd","popoopopo");



        sdv_aty_popup_shopitem_pic = (SimpleDraweeView) popview.findViewById(R.id.sdv_aty_popup_shopitem_pic);
        iv_aty_popup_shopitem_close = (ImageView) popview.findViewById(R.id.iv_aty_popup_shopitem_close);
        tv_aty_popup_shopitem_buy = (TextView) popview.findViewById(R.id.tv_popup_aty_popup_shopitem_buy);
        sdv_aty_popup_shopitem_pic.setImageURI(shopItemList.get(postion).getIcon());
        iv_aty_popup_shopitem_close.setOnClickListener(new MyShopItemClickListener(postion));
        tv_aty_popup_shopitem_buy.setOnClickListener(new MyShopItemClickListener(postion));

        popupWindow.setOnDismissListener(this);
        popupWindow.setTouchable(true);
        popupWindow.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//防止软件盘被弹窗遮挡
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),(Bitmap) null));
        //showAsDropDown是按下显
//        popupWindow.showAsDropDown(fl_aty_thing_cart,0,-900, Gravity.TOP);
        //如果要定位精确显示popwindow那就使用showAtLocation
        popupWindow.showAtLocation(fl_aty_shop_parent, Gravity.CENTER,0,0);
        backgroundAlpha(0.4f);


    }

    //弹出窗消失的监听事件-----------popwindow1---------------------------------------------------------------------
    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
    }





    //自定义的点击监听-----------------------------------------------------------------------------------------
    private class MyShopItemClickListener implements View.OnClickListener{
        private int pos;
        public MyShopItemClickListener(int pos){
            this.pos = pos;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //关闭弹出窗
                case R.id.iv_aty_popup_shopitem_close:
                    popupWindow.dismiss();
                    break;
                //确定购买
                case R.id.tv_popup_aty_popup_shopitem_buy:
                    if(MyApplication.getInstance().myUser==null){
                      SnakeBarUtil.shortBar(fl_aty_shop_parent,"登录后才能购买");
                        return;
                    }
                    ToastUtil.toast("确定购买,将消耗"+shopItemList.get(pos).getScore()+"积分");
                    //购买积分物品

                    int scoreBefore = MyApplication.getInstance().myUser.getScore();
                    if(scoreBefore>=shopItemList.get(pos).getScore()){//当前购买的物品积分少于原有积分(可以购买)
                        buy(MyApplication.getInstance().myUser.getId(),shopItemList.get(pos).getId(),shopItemList.get(pos).getIcon(),
                                shopItemList.get(pos).getDesc(),shopItemList.get(pos).getScore());
                    }else {
                        SnakeBarUtil.shortBar(fl_aty_shop_parent,"积分不足");
                    }
                    break;
            }
        }
    }



    //购买积分物品---------------------------------------------------------------------------------------------------------
    private void buy(int uid,int sid,String icon,String description, final int score) {
        new ScorePresenter().useScore(uid,sid,icon,description,score)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ScoreResEntity>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("shopbuyerror",e.toString());
                    }
                    @Override
                    public void onNext(ScoreResEntity entity) {
                        ToastUtil.toast(entity.getMsg());
                        if(entity.getCode()==1){//购买成功  扣除积分,刷新订单列表
                            int scoreBefore = MyApplication.getInstance().myUser.getScore();
                            MyApplication.getInstance().myUser.setScore(scoreBefore-score);
                            //通知订单列表和个人信息列表刷新
                            EventBus.getDefault().post(new MyEvent(MyEvent.SCORE_COST_SUCCESS));
                            ActivityShop.this.finish();
                        }
                    }
                });
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


    //popwindow2-----------------------------------------------------------------------------------------------
    private void popWindow2() {
        if(popupWindow2==null||popView2==null) {
            popView2 = getLayoutInflater().inflate(R.layout.popup_aty_shop_more, null);
            popupWindow2 = new PopupWindow(popView2, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
        }

        tv_aty_popup_score_history = (TextView) popView2.findViewById(R.id.tv_aty_popup_score_history);
        tv_aty_popup_score_history.setOnClickListener(this);
        popupWindow2.setOnDismissListener(this);
        popupWindow2.setTouchable(true);
        popupWindow2.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//防止软件盘被弹窗遮挡
        popupWindow2.setOutsideTouchable(true);
        popupWindow2.setBackgroundDrawable(new BitmapDrawable(getResources(),(Bitmap) null));
        //showAsDropDown是按下显
//        popupWindow.showAsDropDown(fl_aty_thing_cart,0,-900, Gravity.TOP);
        //如果要定位精确显示popwindow那就使用showAtLocation
        popupWindow2.showAsDropDown(iv_aty_shop_more,0,0,Gravity.CENTER);
        backgroundAlpha(0.4f);

    }

    //popview2---------------------------------点击事件--------------------------------------------------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_aty_popup_score_history:
                if(MyApplication.getInstance().myUser==null){
                    ToastUtil.toast("请先登陆");
                    return;
                }
                ToastUtil.toast("显示兑换列表");
                openActivity(ActivityScoreHistory.class);
                popupWindow2.dismiss();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(popupWindow2!=null&&popupWindow2.isShowing()){
            popupWindow2.dismiss();
        }
        if(popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
        }

    }
}
