package com.example.xonvi.washing2.aty;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xonvi.washing2.Entity.CartListEvent;
import com.example.xonvi.washing2.Entity.MkOrderEvent;
import com.example.xonvi.washing2.Entity.Thing;
import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.adapter.CartListAdp;
import com.example.xonvi.washing2.adapter.TingsFrgPagerAdp;
import com.example.xonvi.washing2.app.MyApplication;
import com.example.xonvi.washing2.fragment.FragmentAccessory;
import com.example.xonvi.washing2.fragment.FragmentBottom;
import com.example.xonvi.washing2.fragment.FragmentShoes;
import com.example.xonvi.washing2.fragment.FragmentTop;
import com.example.xonvi.washing2.util.ThingUtil;
import com.example.xonvi.washing2.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xonvi on 2017/2/5.
 * 加载呈现所有支持清洗的物品
 */


public class ActivityThings extends BaseActivity implements ViewPager.OnPageChangeListener,
        TabLayout.OnTabSelectedListener {

    //路径
    private PathMeasure mPathMeasure;
    /**
     * 贝塞尔曲线中间过程的点的坐标
     */
    private float[] mCurrentPosition = new float[2];

    //右侧滑出的购物车
    @BindView(R.id.ll_aty_things_cart)LinearLayout ll_aty_things_cart;
    //drawerlayout
    @BindView(R.id.drawer_aty_things)DrawerLayout drawer_aty_things;

    //弹出窗的参照布局
    @BindView(R.id.ll_parent)RelativeLayout ll_parent;
    //统计购物车中物品数目的红色小圆点
    @BindView(R.id.tv_red_small_dot)TextView tv_red_small_dot;
    //viewpager
    @BindView(R.id.vp_aty_things)ViewPager vp_aty_things;
    //物品页面的tablayout
    @BindView(R.id.tab_aty_things)TabLayout tab_aty_things;
    //购物车图标
    @BindView(R.id.iv_aty_things_cart)ImageView iv_aty_things_cart;

    private List<TabLayout.Tab> tabList;
    private List<Fragment> fragments;

    //购物车列表物品的集合
    private List<Thing> cartList;


    //当购物车为空时显示的文本
    @BindView(R.id.tv_popwin_when_cart_empty)TextView tv_popwin_when_cart_empty;
    //清空购物车列表
    @BindView(R.id.iv_popwin_clear_cart)ImageView tv_popwin_clear_cart;
    //列表物品总价
    @BindView(R.id.tv_popwin_total)TextView tv_popwin_total;
    //popwindow上的listview既购物车物品列表
    @BindView(R.id.lv_popwin_cart_list)ListView lv_popwin_cart_list;
    //预约下单按钮
    @BindView(R.id.tv_popwin_make_order)TextView tv_popwin_make_order;


    @OnClick({R.id.iv_aty_things_back,R.id.iv_aty_things_cart,R.id.iv_popwin_clear_cart,R.id.tv_popwin_make_order})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_things_back:
                ActivityThings.this.finish();
                break;
            //点击购物车显示购物车列表
            case R.id.iv_aty_things_cart:
                Log.e("fff","dddddddddddd");
                initCart(cartList);
                drawer_aty_things.openDrawer(ll_aty_things_cart);

                break;
            //清空购物车
            case R.id.iv_popwin_clear_cart:
                for (Thing t:cartList){//将之前统计的数目清零
                    t.setCount(1);
                }
                cartList.clear();
                lv_popwin_cart_list.setVisibility(View.GONE);
                tv_popwin_when_cart_empty.setVisibility(View.VISIBLE);
                tv_red_small_dot.setVisibility(View.GONE);
                tv_red_small_dot.setText(0+"");
                tv_popwin_total.setText(0+"");
                break;
            //下单按钮
            case R.id.tv_popwin_make_order:
                //判断用户是否登陆
                if(MyApplication.getInstance().myUser==null){
                    ToastUtil.toast("请先登录");
                    openActivity(ActivityLogin.class);
                    return;
                }
                //将购物车中最后选定的物品传递
                if(!cartList.isEmpty()){
                    ArrayList<Thing> arrayList = (ArrayList<Thing>) cartList;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("cartlist",arrayList);
                    openActivity(ActivityMkOrder.class,bundle);
                }else {
                    ToastUtil.toast("购物车不能为空");
                }
                Log.e("mkmk","mkmkmkmk");

                break;
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_things);
        ButterKnife.bind(this);
        //EventBus注册
        EventBus.getDefault().register(this);
        initData();
    }

    private void initData() {
        //init tablist
        tabList = new ArrayList<>();
        tabList.add(tab_aty_things.newTab().setText("衣服"));
        tabList.add(tab_aty_things.newTab().setText("裤裙"));
        tabList.add(tab_aty_things.newTab().setText("鞋靴"));
        tabList.add(tab_aty_things.newTab().setText("配饰"));
        for(TabLayout.Tab tab : tabList){
            tab_aty_things.addTab(tab);
        }

        //initFragment
        fragments = new ArrayList<>();
        fragments.add(new FragmentTop());
        fragments.add(new FragmentBottom());
        fragments.add(new FragmentShoes());
        fragments.add(new FragmentAccessory());

        //填充fragment页面到viewpager
        TingsFrgPagerAdp pagerAdp = new TingsFrgPagerAdp(getSupportFragmentManager(),fragments);
        vp_aty_things.setAdapter(pagerAdp);
        //设置pager页面改变的监听
        vp_aty_things.setOnPageChangeListener(this);
        //设置tab指示器改变的监听
        tab_aty_things.setOnTabSelectedListener(this);


        //初始化购物侧列表
        cartList = new ArrayList<>();


        //判断点击的那个模块进入 就加载哪个类型的物品
        Log.e("thing",getIntent().getIntExtra("thing",0)+" ");
        switch (getIntent().getIntExtra("thing",0)){

            case ThingUtil.TOP:
                vp_aty_things.setCurrentItem(0);
                break;
            case ThingUtil.BOTTOM:
                vp_aty_things.setCurrentItem(1);
                break;
            case ThingUtil.SHOES:
                vp_aty_things.setCurrentItem(2);
                break;
            case ThingUtil.ACCESSORY:
                vp_aty_things.setCurrentItem(3);
                break;
        }


    }

    //cart-购物车-------------------------------------------------------------------------------------------------------------------------------------------

    private  void initCart(List<Thing> things){
        Log.e("opencart","isopne"+things.size());
        //购物车中有物品
        if(things.size()>0){
            initCartList(things,lv_popwin_cart_list);
            lv_popwin_cart_list.setVisibility(View.VISIBLE);
            tv_popwin_when_cart_empty.setVisibility(View.GONE);
            int total = 0;
            for(Thing thing : things){
                //那么每一项的价格 就是 物品的单价乘以数量
                total+=(thing.getPrice()*thing.getCount());
            }
            tv_popwin_total.setText(total+"");
            //购物车列表为空
        }else {
            tv_popwin_when_cart_empty.setVisibility(View.VISIBLE);
            lv_popwin_cart_list.setVisibility(View.GONE);
        }


    }




    //popupwindow------------------------------------------------------------------------

    //弹出窗
//    private void popWindow(List<Thing> things) {
//        if(popupWindow==null||popupWindow==null) {
//            popview = getLayoutInflater().inflate(R.layout.aty_thing_popup_window, null);
//            popupWindow = new PopupWindow(popview, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,true);
//        }
//        Log.e("ddd","popoopopo");
//        tv_popwin_when_cart_empty = (TextView)popview.findViewById(R.id.tv_popwin_when_cart_empty);
//        tv_popwin_clear_cart = (TextView) popview.findViewById(R.id.tv_popwin_clear_cart);
//        tv_popwin_close_cart = (TextView) popview.findViewById(R.id.tv_popwin_close_cart);
//        tv_popwin_total = (TextView) popview.findViewById(R.id.tv_popwin_total);
//        tv_popwin_make_order=(TextView)popview.findViewById(R.id.tv_popwin_make_order);
//        lv_popwin_cart_list = (ListView) popview.findViewById(R.id.lv_popwin_cart_list);
//        tv_popwin_close_cart.setOnClickListener(this);
//        tv_popwin_clear_cart.setOnClickListener(this);
//        tv_popwin_make_order.setOnClickListener(this);
//
//        //购物车中有物品
//        if(things.size()>0){
//            initCartList(things,lv_popwin_cart_list);
//            lv_popwin_cart_list.setVisibility(View.VISIBLE);
//            tv_popwin_when_cart_empty.setVisibility(View.GONE);
//            int total = 0;
//            for(Thing thing : things){
//                //那么每一项的价格 就是 物品的单价乘以数量
//                total+=(thing.getPrice()*thing.getCount());
//            }
//            tv_popwin_total.setText(total+"");
//            //购物车列表为空
//        }else {
//            tv_popwin_when_cart_empty.setVisibility(View.VISIBLE);
//            lv_popwin_cart_list.setVisibility(View.GONE);
//        }
//
//
//        tv_popwin_clear_cart.setOnClickListener(this);
//        tv_popwin_close_cart.setOnClickListener(this);
//        popupWindow.setTouchable(true);
//        popupWindow.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//防止软件盘被弹窗遮挡
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),(Bitmap) null));
//        //showAsDropDown是按下显示
////        popupWindow.showAsDropDown(fl_aty_thing_cart,0,-900, Gravity.TOP);
//        //如果要定位精确显示popwindow那就使用showAtLocation
//        popupWindow.showAtLocation(ll_parent,Gravity.BOTTOM,0,0);
//    }

    //初始化购物车列表
    private void initCartList(List<Thing> thingList,ListView cartList) {
        //适配加入购物车的物品列表
        CartListAdp cartListAdp = new CartListAdp(thingList);
        cartList.setAdapter(cartListAdp);
    }


    //eventbus--------------------------------------------------------------------------------

    //在物品页选取添加物品
    //在 popwindow的listview中调整物品的数量
    @Subscribe
    public void onEventMainThread(CartListEvent event){
        switch (event.TYPE){
            case CartListEvent.ADD:
                //相应的 总价要增加
                int totala = Integer.parseInt(tv_popwin_total.getText().toString());
                totala+=event.getThing().getPrice();
                tv_popwin_total.setText(totala+"");
                break;
            case CartListEvent.REMOVE:
                //相应的 总价要减少
                int totalr = Integer.parseInt(tv_popwin_total.getText().toString());
                Log.e("remove",totalr+" ");
                totalr-=event.getThing().getPrice();
                tv_popwin_total.setText(totalr+"");
                break;
            case CartListEvent.EMPTY:
                lv_popwin_cart_list.setVisibility(View.GONE);
                tv_popwin_when_cart_empty.setVisibility(View.VISIBLE);
                tv_red_small_dot.setVisibility(View.GONE);
                tv_popwin_total.setText(0+"");
                break;
            case CartListEvent.ADD_TO_CART:
                //从物品列表发送一次消息过来 就将点击的对应的物品添加进购物车

                addCart(event.getIv());
                if(cartList.contains(event.getThing())){
                    //这件物品在购物车列表 那么不将其再以一个item的形式展现 而是将已经存在的item数目加一
                    int count = event.getThing().getCount();
                    count++;
                    event.getThing().setCount(count);
                }else {
                    //这件物品没有在购物车列表 就直接添加
                    cartList.add(event.getThing());
                }
                if(cartList.size()>0){
                    //购物车中物品的数目就是列表的长度
                    tv_red_small_dot.setVisibility(View.VISIBLE);
                    //红色小圆点显示的数字
                    //统计的是物品总的个数(包含重复的物品)
                    int dot_num = 0;
                    for(Thing things:cartList){
                        dot_num+=things.getCount();
                    }
                    tv_red_small_dot.setText(dot_num+"");


                }
                break;
            case CartListEvent.SUBMIT_COMPLETED:
                cartList.clear();
              drawer_aty_things.closeDrawers();
                tv_red_small_dot.setVisibility(View.GONE);
                break;
        }

    }



    //viewpager-------------------------------------------------------------
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //每次viewpager选中对应的tab也选中
        tabList.get(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    //tab------------------------------------------------------
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //当tab被选中的时候 让对应的pager页面也选中
        if(tab.equals(tabList.get(0))){
            vp_aty_things.setCurrentItem(0);
        }else if(tab.equals(tabList.get(1))){
            vp_aty_things.setCurrentItem(1);
        }else if(tab.equals(tabList.get(2))){
            vp_aty_things.setCurrentItem(2);
        }else if(tab.equals(tabList.get(3))){
            vp_aty_things.setCurrentItem(3);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    //加入购物车动画效果贝塞尔曲线----------------------------------------------------------------------------------------------
    /**
     * ★★★★★把商品添加到购物车的动画效果★★★★★
     * @param iv
     */
    private void addCart( ImageView iv) {
//   一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        final TextView goods = new TextView(this);
//        goods.setBackgroundResource(R.drawable.circle_red_bg);
        goods.setBackgroundResource(R.drawable.red_circle_bg);
        goods.setText("1");
        goods.setTextColor(Color.parseColor("#FFFFFF"));
        goods.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
        ll_parent.addView(goods, params);

//    二、计算动画开始/结束点的坐标的准备工作
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        ll_parent.getLocationInWindow(parentLocation);

        //得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        iv.getLocationInWindow(startLoc);

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        iv_aty_things_cart.getLocationInWindow(endLoc);


//    三、正式开始计算动画开始/结束的坐标
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + iv.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + iv.getHeight() / 2;

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLoc[0] - parentLocation[0] + iv_aty_things_cart.getWidth() / 5;
        float toY = endLoc[1] - parentLocation[1];

//    四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);

        //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(800);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // ★★★★★获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
//   五、 开始执行动画
        valueAnimator.start();

//   六、动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            //曲线动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
//                // 把移动的图片imageview从父布局里移除(从页面把动画生成图像移除)
                ll_parent.removeView(goods);
                //结束动画
                Animation afterAdd = AnimationUtils.loadAnimation(ActivityThings.this, R.anim.add_cart_anim);
                tv_red_small_dot.setAnimation(afterAdd);
                tv_red_small_dot.startAnimation(afterAdd);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        //f反注册
        EventBus.getDefault().unregister(this);
    }



}
