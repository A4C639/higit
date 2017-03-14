package com.example.xonvi.washing2.aty;

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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.ScoreHisDelResEntity;
import com.example.xonvi.washing2.Entity.ScoreThingResEntity;
import com.example.xonvi.washing2.Entity.ShopItem;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.ScoreHisRVAdp;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.presenter.ScoreHisDelPresenter;
import com.example.xonvi.washing2.presenter.ScoreHisPresenter;
import com.example.xonvi.washing2.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xonvi on 2017/3/1.
 */

//积分兑换历史记录
public class ActivityScoreHistory extends BaseActivity implements ScoreHisRVAdp.MyScoreHisClickListener, PopupWindow.OnDismissListener {

    //加载动画
    @BindView(R.id.fl_loading_anima_aty_score_history)FrameLayout fl_loading_anima_aty_score_history;
    //积分兑换历史记录列表的适配器
    private ScoreHisRVAdp scoreHisRVAdp;

    private TextView tv_aty_popup_score_history_del;
    private PopupWindow popupWindow;
    private View popView;
    //保存当前积分兑换历史列表
    private List<ShopItem> scoreThings;
    @BindView(R.id.parent_aty_score_history)LinearLayout parent_aty_score_history;
    //兑换历史的列表
    @BindView(R.id.rv_aty_scorehistory)RecyclerView rv_aty_scorehistory;
    @OnClick({R.id.iv_aty_scorehistory_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_scorehistory_back:
                this.finish();
                break;
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_history);
        ButterKnife.bind(this);
        initScoreHistoryList();
    }


    //初始化 积分兑换列表-------------------------------------------------------------------------------
    private void initScoreHistoryList() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_aty_scorehistory.setLayoutManager(manager);


        reqScoreThingList();


    }


    //请求积分兑换列表信息-------------------------------------------------------------------------------
    private void reqScoreThingList() {
        fl_loading_anima_aty_score_history.setVisibility(View.VISIBLE);
        new ScoreHisPresenter().scoreHistory(MyApplication.getInstance().myUser.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ScoreThingResEntity>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("scorehistoryerror--",e.toString());
                        fl_loading_anima_aty_score_history.setVisibility(View.GONE);
                    }
                    @Override
                    public void onNext(ScoreThingResEntity entity) {
                        ToastUtil.toast(entity.getMsg());
                        if(entity.getCode()==1){
                            scoreThings = entity.getData();
                            loadHistoryList(scoreThings);
                        }
                        //停止加载
                        fl_loading_anima_aty_score_history.setVisibility(View.GONE);
                    }
                });

    }


    //将数据填充到列表--------------------------------------------------------------------------------
    private void loadHistoryList(List<ShopItem> scoreThings) {
         scoreHisRVAdp = new ScoreHisRVAdp(scoreThings);
        scoreHisRVAdp.setMyScoreHisClickListener(this);
        rv_aty_scorehistory.setAdapter(scoreHisRVAdp);

    }


    //积分兑换列表的点击事件-------------------------------------------------------------------------
    @Override
    public void myScoreHisItemClick(int position, View view) {
        Log.e("myScoreHisItemClick","myScoreHisItemClick-----"+position);
        popWindow(position);
    }


    //弹出窗-----------------------------------------------------------------------------------------
    private void popWindow(int position) {
            if(popupWindow==null||popView==null) {
                popView = getLayoutInflater().inflate(R.layout.popup_aty_scorehis_del, null);
                popupWindow = new PopupWindow(popView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
            }
            tv_aty_popup_score_history_del = (TextView) popView.findViewById(R.id.tv_aty_popup_score_history_del);
            tv_aty_popup_score_history_del.setOnClickListener(new MyScoreHisListener(position));
            popupWindow.setOnDismissListener(this);
            popupWindow.setTouchable(true);
            popupWindow.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//防止软件盘被弹窗遮挡
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),(Bitmap) null));
            //showAsDropDown是按下显
//        popupWindow.showAsDropDown(fl_aty_thing_cart,0,-900, Gravity.TOP);
            //如果要定位精确显示popwindow那就使用showAtLocation
            popupWindow.showAtLocation(parent_aty_score_history,Gravity.CENTER,0,0);
            backgroundAlpha(0.4f);
    }


    /**
     * 设置添加屏幕的背景透明度-------------------------------------------------------------------------
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    //t弹出窗消失的监听------------------------------------------------------------------------------------------------
    @Override
    public void onDismiss() {
        backgroundAlpha(1f);

    }


    //自定义点击事件类-----------------------------------------------------------------------------------
    public class MyScoreHisListener implements View.OnClickListener{
        private int position;
        public MyScoreHisListener(int position){
            this.position = position;

        }
        @Override
        public void onClick(View v) {
            new ScoreHisDelPresenter().delHis(scoreThings.get(position).getId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<ScoreHisDelResEntity>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            Log.e("delscorehiserror",e.toString());
                        }
                        @Override
                        public void onNext(ScoreHisDelResEntity entity) {
                            ToastUtil.toast(entity.getMsg());
                            if(entity.getCode()==1){
                                scoreHisRVAdp.delElement(position);
                                popupWindow.dismiss();
                            }
                        }
                    });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }
}
