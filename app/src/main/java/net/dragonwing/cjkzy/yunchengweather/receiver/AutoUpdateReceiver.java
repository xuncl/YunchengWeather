package net.dragonwing.cjkzy.yunchengweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by CLEVO on 2015/12/11.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpdateReceiver.class);
        context.startService(i);
    }
}
