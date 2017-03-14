package com.example.xonvi.washing2.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by xonvi on 2017/2/10.
 */

//格式化当前时间的工具类

public class TimeUitl {

    public static String getTime(){


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm");
        return simpleDateFormat.format(Calendar.getInstance().getTime());


    }
}
