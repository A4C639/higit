package com.example.xonvi.washing2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.xonvi.washing2.aty.ActivityIndex;

/**
 * Created by xonvi on 2017/2/27.
 */

//开机启动的广播
public class BootReceiver extends BroadcastReceiver {
    private final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(ACTION_BOOT)){
            Intent boot = new Intent(context,ActivityIndex.class);
            boot.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(boot);
        }
    }
}
