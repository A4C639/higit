package com.example.xonvi.washing2.aty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.xonvi.washing2.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xonvi on 2017/2/13.
 */

public class ActivitySecurity extends BaseActivity {

    @OnClick({R.id.iv_aty_security_back,R.id.rl_aty_security_edit_pass})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_aty_security_back:
                this.finish();
                break;
            //修改密码
            case R.id.rl_aty_security_edit_pass:
                openActivity(ActivityEditPwd.class);
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);
    }
}
