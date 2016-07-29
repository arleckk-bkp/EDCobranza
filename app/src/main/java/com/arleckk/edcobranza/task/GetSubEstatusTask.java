package com.arleckk.edcobranza.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.arleckk.edcobranza.global.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arleckk on 7/25/16.
 */
public class GetSubEstatusTask extends AsyncTask<String,Void,JSONArray> {

    private ProgressDialog mProgress;
    private Spinner mSubEstatus;
    private Context context;

    public GetSubEstatusTask(Context context, ProgressDialog mProgress, Spinner mSubEstatus) {
        this.context = context;
        this.mProgress = mProgress;
        this.mSubEstatus = mSubEstatus;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setIndeterminate(true);
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        String result = null;
        JSONArray json = null;
        String estatus = params[0];
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(Constants.GET_SUBESTATUS+"?"+"estatus="+estatus);
            HttpResponse response = null;
            response = httpClient.execute(httpGet);
            if(response != null) {
                result = EntityUtils.toString(response.getEntity());
                json = new JSONArray(result);
            }
        } catch (IOException e) {
            mProgress.dismiss();
            e.printStackTrace();
        } catch (JSONException e) {
            mProgress.dismiss();
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        mProgress.dismiss();

        List<String> list = new ArrayList<String>();

        try {
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                list.add(json.getString("subestatus"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        mSubEstatus.setAdapter(adapter);
    }


}
