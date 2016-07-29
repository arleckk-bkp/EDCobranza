package com.arleckk.edcobranza.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by arleckk on 7/20/16.
 */
public class UpdateLocationBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("broadcast_debug","se recibio en updatelocationbroadcast");
//        Toast.makeText(context,"se recibio en updatelocationbroadcast",Toast.LENGTH_SHORT).show();
    }
}
