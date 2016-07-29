package com.arleckk.edcobranza.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.arleckk.edcobranza.global.Cifrado;
import com.arleckk.edcobranza.global.Utilities;
import com.arleckk.edcobranza.task.UpdateLocationTask;

import java.io.UnsupportedEncodingException;

/**
 * Created by arleckk on 12/07/16.
 */
public class UpdateLocation extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null) {
            if(intent.getExtras() != null) {
                if(intent.getExtras().get("location") != null) {
//                    UpdateLocationTask
                    UpdateLocationTask task = new UpdateLocationTask();
                    SharedPreferences sharedPreferences = getSharedPreferences("preferencias",MODE_PRIVATE);
                    String user = sharedPreferences.getString("user","null");
                    Location location = (Location) intent.getExtras().get("location");
                    try {
                        user = new Cifrado().decrypt(user);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String [] params = {
                        user,
                        String.valueOf(location.getLatitude()),
                        String.valueOf(location.getLongitude()),
                        Utilities.getDate() + " " + Utilities.getHour()
                    };
                    task.execute(params);
                }
            }
        }
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
