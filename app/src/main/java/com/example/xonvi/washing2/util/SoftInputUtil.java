package com.example.xonvi.washing2.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by xonvi on 2017/3/6.
 */

//隐藏虚拟键盘
public class SoftInputUtil {


    //如果软盘开启那么就隐藏 如果隐藏那么就开启
    public static void hiddenSoftInputNotAways(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //隐藏软键盘
    public static void hiddenSoftInput(EditText editText,Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
        }
    }


}
