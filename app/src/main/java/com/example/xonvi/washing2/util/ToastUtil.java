package com.example.xonvi.washing2.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xonvi on 2016/12/26.
 */

public class ToastUtil {
   private static Toast toast;

    public static void initToast(Context context){
        Toast makeText = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        toast = makeText;
    }

    public static void toast(String str){
        toast.setText(str);
        toast.show();
    }
}
