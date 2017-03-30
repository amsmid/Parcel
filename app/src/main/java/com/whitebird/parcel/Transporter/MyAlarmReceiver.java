package com.whitebird.parcel.Transporter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by girish on 22/3/17.
 */

public class MyAlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyAlarmReceiver","Start");
        Intent i = new Intent(context, MyGpsTrackerService.class);
        context.startService(i);
    }
}
