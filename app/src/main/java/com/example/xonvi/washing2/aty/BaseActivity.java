package com.example.xonvi.washing2.aty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by xonvi on 2016/12/12.
 */

public class BaseActivity extends FragmentActivity {

    //activity不带参数的跳转
    protected void openActivity(Class<?> targetAct){
        Intent intent = new Intent(this,targetAct);
        startActivity(intent);
    }


    //携带参数
    protected void openActivity(Class<?> targetAct, Bundle value){
        Intent intent = new Intent(this,targetAct);
        intent.putExtras(value);
        startActivity(intent);
    }
}
