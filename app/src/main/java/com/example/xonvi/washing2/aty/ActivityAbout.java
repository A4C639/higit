package com.example.xonvi.washing2.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.xonvi.washing2.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xonvi on 2017/2/17.
 */

//关于页面
public class ActivityAbout extends BaseActivity {

    @OnClick({R.id.iv_aty_about_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_about_back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }
}
