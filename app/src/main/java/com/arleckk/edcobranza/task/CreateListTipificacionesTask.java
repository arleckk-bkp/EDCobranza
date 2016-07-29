package com.arleckk.edcobranza.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.arleckk.edcobranza.adapter.CapturaAdapter;
import com.arleckk.edcobranza.global.Constants;
import com.arleckk.edcobranza.model.Tipificacion;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by arleckk on 7/21/16.
 */
public class CreateListTipificacionesTask extends AsyncTask<ArrayList<Tipificacion>,Void,ArrayList<Tipificacion>> {

    private ListView mTipificaciones;
    private Context context;

    public CreateListTipificacionesTask(Context context, ListView mTipificaciones) {
        this.mTipificaciones = mTipificaciones;
        this.context = context;
    }

    @Override
    protected ArrayList<Tipificacion> doInBackground(ArrayList<Tipificacion>... params) {

        ArrayList<Tipificacion> tipificaciones = params[0];

        return tipificaciones;
    }

    @Override
    protected void onPostExecute(ArrayList<Tipificacion> tipificacions) {
        super.onPostExecute(tipificacions);
        if(tipificacions != null) {
            if(context != null) {
                Log.v("ERROR_NULL","context != null");
                mTipificaciones.setAdapter(new CapturaAdapter(context,tipificacions));
            } else {
                Log.v("ERROR_NULL","context == null");
            }
        } else {
            Toast.makeText(context,"No hay tipificaciones", Toast.LENGTH_SHORT).show();
//            mTipificaciones.setAdapter(new ArrayAdapter<>());
        }
    }
}
