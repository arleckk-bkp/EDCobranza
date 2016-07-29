package com.arleckk.edcobranza.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.arleckk.edcobranza.global.Constants;
import com.arleckk.edcobranza.global.VolleyEverywhere;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arleckk on 7/18/16.
 */
public class UpdateLocationTask extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... params) {
        JSONObject json = new JSONObject();
        String res = "";
        try {
            json.put("user",params[0]);
            json.put("latitude", params[1]);
            json.put("longitude", params[2]);
            json.put("timestamp",params[3]);
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(Constants.POST_LOCATION);
            HttpResponse response;
            StringEntity entity = new StringEntity(json.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setHeader("Accept","application/json");
            post.setHeader("Content-Type","application/json");
            post.setEntity(entity);
            response = client.execute(post);
            res = EntityUtils.toString(response.getEntity());
        } catch(JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.v("volley_debug", "debug: " + s);
    }
}
