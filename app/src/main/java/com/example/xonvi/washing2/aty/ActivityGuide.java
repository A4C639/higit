package com.example.xonvi.washing2.aty;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.xonvi.washing2.R;
import com.example.xonvi.washing2.util.StatusBarTintUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xonvi on 2017/2/13.
 */

//引导页

public class ActivityGuide extends BaseActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ActivityGuide.this.finish();

        }
    };

    @BindView(R.id.tv_aty_guide_logo)TextView tv_aty_guide_logo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置屏幕参数 让activity可以顶到状态栏显示 必须在setContentview之前
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_guide);

        ButterKnife.bind(this);
        //渲染状态栏
        StatusBarTintUtil.initSystemBar(this);

        setTypeFace();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);

            }
        },4000);
    }

    //设置字体样式
    private void setTypeFace() {

        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "Streetwear.otf");
        tv_aty_guide_logo.setTypeface(typeface);
    }
}
