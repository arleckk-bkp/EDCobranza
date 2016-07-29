package com.arleckk.edcobranza.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
public class GetEstatusTask extends AsyncTask<Void,Void,JSONArray> {

    private Spinner mEstatus;
    private ProgressDialog mProgress;
    private Context context;

    public GetEstatusTask(Context context, ProgressDialog mProgress, Spinner mEstatus) {
        this.context = context;
        this.mProgress = mProgress;
        this.mEstatus = mEstatus;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(mProgress.isShowing()) {
            mProgress.dismiss();
        }
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setIndeterminate(true);
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    protected JSONArray doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(Constants.GET_ESTATUS);
        String result = null;
        JSONArray json = null;
        HttpResponse response = null;

        try {
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
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                list.add((String) json.get("estatus"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,list);
        mEstatus.setAdapter(adapter);
    }

}
