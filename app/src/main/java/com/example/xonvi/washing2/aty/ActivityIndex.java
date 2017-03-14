package com.example.xonvi.washing2.aty;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.MyEvent;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.MyFragAdapter;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.fragment.Fragment_BillList;
import com.example.xonvi.washing2.fragment.Fragment_Identity;
import com.example.xonvi.washing2.fragment.Fragment_Index;
import com.example.xonvi.washing2.manager.SystemBarTintManager;
import com.example.xonvi.washing2.util.AnimatorUtils;
import com.example.xonvi.washing2.util.SnakeBarUtil;
import com.example.xonvi.washing2.util.StatusBarTintUtil;
import com.example.xonvi.washing2.util.ToastUtil;
import com.ogaclejapan.arclayout.Arc;
import com.ogaclejapan.arclayout.ArcLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xonvi on 2016/12/12.
 */

public class ActivityIndex extends BaseActivity implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    //给个人信息页面发送事件通知的接口
    private ISendToFrgIdentity iSendToFrgIdentity;
    //给账单列表发送事件通知的接口
    private ISendToFrgBilist iSendToFrgBilist;

    public void setiSendToFrgIdentity(ISendToFrgIdentity iSendToFrgIdentity){
        this.iSendToFrgIdentity = iSendToFrgIdentity;
    }
    public void setiSendToFrgBilist(ISendToFrgBilist iSendToFrgBilist){
        this.iSendToFrgBilist = iSendToFrgBilist;
    }

    public interface ISendToFrgIdentity {
        void msgGet(MyEvent event);
    }
    public interface ISendToFrgBilist {
        void msgGet(MyEvent event);
    }


    //arclayout
    private ArcLayout al_aty_index;
    //arclayout 动画显示的布局
    @BindView(R.id.fl_aty_index_menu_layout)FrameLayout fl_aty_index_menu_layout;
    //浮动按钮
    @BindView(R.id.fab_aty_index)FloatingActionButton fab_aty_index;
    //侧滑栏显示退出登陆的字符
    @BindView(R.id.tv_index_drawer_left_exit)TextView tv_index_drawer_left_exit;
    // 主页顶部包括tabs
    @BindView(R.id.ll_index)LinearLayout ll_index;
    //app的compus文本标志
    @BindView(R.id.tv_logo_left)TextView tv_logo_left;
    @BindView(R.id.tv_app)TextView tv_app;
    //tablayout
    @BindView(R.id.index_tablayout)TabLayout index_tablayout;
    //主页底部导航栏
    @BindView(R.id.iv_index_bilist)ImageView iv_index_bilist;
    @BindView(R.id.iv_index_home)ImageView iv_index_home;
    @BindView(R.id.iv_index_identity)ImageView iv_index_identity;
    //viewpager
    @BindView(R.id.index_vp)ViewPager viewPager;
    //drawerlayout
    @BindView(R.id.layout_index_drawer)DrawerLayout drawerLayout;
    //drawerlayout打开的页面(左侧抽屉页面)
    @BindView(R.id.index_frame_drawer)LinearLayout frameLayout;
    //打开抽屉页面的图标开关 垂直横线条
    @BindView(R.id.iv_index_more)ImageView iv_index_more;
    @OnClick({R.id.iv_index_more,R.id.iv_index_identity,R.id.iv_index_home,R.id.iv_index_bilist,R.id.index_drawer_left_exit,
            R.id.rl_drawer_left_home,R.id.rl_drawer_left_aboutus, R.id.rl_drawer_left_suggestion,R.id.rl_drawer_left_wash_teach,
            R.id.fab_aty_index,R.id.btn_aty_index_comment,R.id.btn_aty_index_introduce,R.id.btn_aty_index_price})
    public void click(View view){
        switch (view.getId()){
            //浮动按钮
            case R.id.fab_aty_index:
                onFabClick(fab_aty_index);
                break;
            //评价
            case R.id.btn_aty_index_comment:
                openActivity(ActivityComment.class);
                break;
            //服务介绍
            case R.id.btn_aty_index_introduce:
                openActivity(ActivityIntroduce.class);
                break;
            //价目中心
            case R.id.btn_aty_index_price:
                openActivity(ActivityPrice.class);
                break;
            //打开抽屉
            case R.id.iv_index_more:
                drawerLayout.openDrawer(frameLayout);
                break;
            //底部导航 主页
            case R.id.iv_index_home:
                viewPager.setCurrentItem(0);
                break;
            //底部导航 账单
            case R.id.iv_index_bilist:
                viewPager.setCurrentItem(1);
                break;
            //底部导航 个人信息
            case R.id.iv_index_identity:
                viewPager.setCurrentItem(2);
                break;
            //退出登陆
            case R.id.index_drawer_left_exit:
                //判断用户是否登陆
                if(MyApplication.getInstance().myUser==null){
                    SnakeBarUtil.shortBar(drawerLayout,"还没有登陆哦亲");
                    openActivity(ActivityLogin.class);
                    return;
                }
                //将application中用户数据清空
               MyApplication.getInstance().myUser=null;
                SnakeBarUtil.shortBar(drawerLayout,"注销成功");
                //关闭抽屉
                drawerLayout.closeDrawer(frameLayout);
                EventBus.getDefault().post(new MyEvent(MyEvent.LOGUOT));
                break;
            //意见反馈
            case R.id.rl_drawer_left_suggestion:
                if(MyApplication.getInstance().myUser==null){
                    SnakeBarUtil.shortBar(drawerLayout,"请先登录");
                    return;
                }
                openActivity(ActivityFeedback.class);
                break;
            //关于我们
            case R.id.rl_drawer_left_aboutus:
                openActivity(ActivityAbout.class);
                break;
            //返回主页
            case R.id.rl_drawer_left_home:
                drawerLayout.closeDrawers();
                viewPager.setCurrentItem(0);
                break;
            //洗衣技巧
            case R.id.rl_drawer_left_wash_teach:
                openActivity(ActivitySkill.class);
                break;
        }
    }


    private List<Fragment> fragments;
    private List<TabLayout.Tab> tabList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载引导页
        openActivity(ActivityGuide.class);
        //设置屏幕参数 让activity可以顶到状态栏显示 必须在setContentview之前
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_index);

        //渲染状态栏
        StatusBarTintUtil.initSystemBar(this);
        //butterknife
        ButterKnife.bind(this);

        //注册eventbus
        EventBus.getDefault().register(this);

        //arc layout  设置为右下角 90度展开
        al_aty_index = (ArcLayout) findViewById(R.id.al_aty_index);
        al_aty_index.setArc(Arc.BOTTOM_RIGHT);

        initData();
    }

    private void initData(){
        //设置顶部标签文本的字体
        AssetManager assetManager = getAssets();
            Typeface typeface = Typeface.createFromAsset(assetManager, "Streetwear.otf");
        tv_app.setTypeface(typeface);
        tv_logo_left.setTypeface(typeface);

        //将fragment添加到一个集合
        fragments = new ArrayList<>();
        tabList = new ArrayList<>();
        fragments.add(new Fragment_Index());
        fragments.add(new Fragment_BillList());
        fragments.add(new Fragment_Identity());
        //tablayout设置
        tabList.add(index_tablayout.newTab().setText("主页"));
        tabList.add(index_tablayout.newTab().setText("订单"));
        tabList.add(index_tablayout.newTab().setText("我的"));
        //将tab标签添加到tablayout
        for(int i=0;i<tabList.size();i++){
            index_tablayout.addTab(tabList.get(i));
        }
        index_tablayout.setOnTabSelectedListener(this);

        //设置drawerlayout
        drawerLayout.setFocusableInTouchMode(false);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

       //将fragmnt页面填充viewpager
        Log.e("lsit",fragments.size()+" "+new Fragment_Identity().getView());
        MyFragAdapter adapter = new MyFragAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);

    }


    //eventbus----------------------------------------------------------------------------------------------------
    @Subscribe
    public void onEventMainThread(MyEvent event){
        //通过接口回掉通知两个fragment 需要更新

        switch (event.event){
            //不管是登陆事件还是注销事件都通过eventbus发送给到ActivityIndex
            case MyEvent.LOGIN:
                SnakeBarUtil.shortBar(drawerLayout,"登陆成功");
                iSendToFrgBilist.msgGet(event);
                try {
                    iSendToFrgIdentity.msgGet(event);
                }catch (Exception e){
                    Log.e("iSendToFrgIdentity","个人信息页还未初始化");
                }

                tv_index_drawer_left_exit.setText("注销账号");
                drawerLayout.closeDrawers();
                break;
            case MyEvent.LOGUOT:
                iSendToFrgBilist.msgGet(event);
                try {
                    iSendToFrgIdentity.msgGet(event);
                }catch (Exception e){
                    Log.e("iSendToFrgIdentity","个人信息页还未初始化");
                }
                tv_index_drawer_left_exit.setText("登陆");
                drawerLayout.closeDrawers();
                break;
            //充值成功
            case MyEvent.CHARGE_SUCCESS:
                iSendToFrgIdentity.msgGet(event);
                break;
            case MyEvent.PORTRAIT_UPLOAD_SUCCESS:
                iSendToFrgIdentity.msgGet(event);
                break;
            //积分消费成功
            case  MyEvent.SCORE_COST_SUCCESS:
                iSendToFrgIdentity.msgGet(event);
                break;
        }

    }





    //viewpager---------------------------------------------------------------------------------------------------------------------
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
        tabList.get(position).select();
        switch (position){
            case 0:
                iv_index_home.setImageResource(R.mipmap.ic_home_normal);
                iv_index_bilist.setImageResource(R.mipmap.ic_library_books_pressed);
                iv_index_identity.setImageResource(R.mipmap.ic_perm_identity_pressed);
                break;
            case 1:
                iv_index_home.setImageResource(R.mipmap.ic_home_pressed);
                iv_index_bilist.setImageResource(R.mipmap.ic_library_books_normal);
                iv_index_identity.setImageResource(R.mipmap.ic_perm_identity_pressed);
                break;
            case 2:
                iv_index_home.setImageResource(R.mipmap.ic_home_pressed);
                iv_index_bilist.setImageResource(R.mipmap.ic_library_books_pressed);
                iv_index_identity.setImageResource(R.mipmap.ic_perm_identity_normal);
                break;
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }
    //tablayout-------------------------------------------------------------------------------------------------

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.equals(tabList.get(0))){
            viewPager.setCurrentItem(0);
        }else if(tab.equals(tabList.get(1))){
            viewPager.setCurrentItem(1);

        }else if(tab.equals(tabList.get(2))){
            viewPager.setCurrentItem(2);
        }
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }
    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }


    //onkeydown按两次退出应用----------------------------------------------
    private long first_press=0l;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN){
            if(first_press==0l){//第一次按下退出
                //记录按下退出的时间
                first_press = System.currentTimeMillis();
                SnakeBarUtil.shortBar(drawerLayout,"再按一次退出程序");
                return false;
            }else {//3秒内按两次退出程序
                if(System.currentTimeMillis()-first_press<3000l){
                    this.finish();
                    return true;
                }else {//时间超过3秒视为按两次推出程序失效
                    first_press=0l;
                    return false;
                }
            }
        }else {//不是按的回退键
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册eventbus
        EventBus.getDefault().unregister(this);
    }


    //一下是arclayout----------------------------------------------------------------------------------------


    //当按钮选择时
    private void onFabClick(View v) {
        if (v.isSelected()) {//选中状态
            fab_aty_index.setImageResource(R.mipmap.ic_add_white_36dp);
            hideMenu();

        } else {
            fab_aty_index.setImageResource(R.mipmap.ic_remove_black_36dp);

            showMenu();

        }
        v.setSelected(!v.isSelected());
    }

    //显示展开
    private void showMenu() {
        fl_aty_index_menu_layout.setVisibility(View.VISIBLE);
        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = al_aty_index.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(al_aty_index.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    //隐藏布局
    private void hideMenu() {

        List<Animator> animList = new ArrayList<>();

        for (int i = al_aty_index.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(al_aty_index.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fl_aty_index_menu_layout.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();

    }

    //创建隐藏布局动画
    private Animator createHideItemAnimator(final View item) {
        float dx = fab_aty_index.getX() - item.getX();
        float dy = fab_aty_index.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }
    //创建展示布局的动画
    private Animator createShowItemAnimator(View item) {
        float dx = fab_aty_index.getX() - item.getX();
        float dy = fab_aty_index.getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        return anim;
    }
}
