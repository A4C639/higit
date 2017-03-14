package com.example.xonvi.washing2.util;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xonvi on 2017/3/7.
 */

public class SnakeBarUtil {

    public static void shortBar(View root,String text){
        Snackbar snackbar = Snackbar.make(root, text, Snackbar.LENGTH_SHORT);
        TextView snake_text = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snake_text.setTextColor(Color.parseColor("#339999"));
        snackbar.show();

    }

    public static void longBar(View root,String text){

        Snackbar.make(root,text,Snackbar.LENGTH_LONG).show();

    }

    public static void interActBar(View root, String text,String actionname,View.OnClickListener listener){

        Snackbar.make(root,text,Snackbar.LENGTH_INDEFINITE).setAction(actionname,listener).show();
    }
}
