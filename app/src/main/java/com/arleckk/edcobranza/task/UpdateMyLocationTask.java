package com.arleckk.edcobranza.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.arleckk.edcobranza.global.Cifrado;
import com.arleckk.edcobranza.global.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arleckk on 7/28/16.
 */
public class UpdateMyLocationTask extends AsyncTask<Location,Void,JSONObject> {

    private Context context;

    public UpdateMyLocationTask(Context context) {
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(Location... params) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(Constants.UPDATE_LOCATION);
        HttpResponse response = null;
        String result = null;
        JSONObject json = null;
        Cifrado cifrado;
        SharedPreferences sharedPreferences = null;
        sharedPreferences = context.getSharedPreferences("preferencias",context.MODE_PRIVATE);
        cifrado = new Cifrado();
        Location location = params[0];
        try {
            cifrado = new Cifrado();
            List<NameValuePair> paramsPost = new ArrayList<NameValuePair>();
            paramsPost.add(new BasicNameValuePair("latitude",String.valueOf(location.getLatitude())));
            paramsPost.add(new BasicNameValuePair("longitude",String.valueOf(location.getLongitude())));
            paramsPost.add(new BasicNameValuePair("user",cifrado.decrypt(sharedPreferences.getString("user", "null"))));
            httpPost.setEntity(new UrlEncodedFormEntity(paramsPost));
            response = httpClient.execute(httpPost);

            if(response != null) {
                result = EntityUtils.toString(response.getEntity());
            }

            json = new JSONObject(result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        Log.v("SPY_DEBUG","JSON: "+jsonObject.toString());
    }

}
