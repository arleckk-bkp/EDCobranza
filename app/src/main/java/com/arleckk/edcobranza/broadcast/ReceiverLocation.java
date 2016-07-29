package com.arleckk.edcobranza.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.arleckk.edcobranza.services.UpdateLocation;
import com.arleckk.edcobranza.ui.GestorActivity;

/**
 * Created by arleckk on 7/20/16.
 */
public class ReceiverLocation extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context,"se recibio en receiverlocation",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, UpdateLocation.class);
        i.putExtra("location", (Location)intent.getExtras().get("location"));
        context.startService(i);
    }
}
