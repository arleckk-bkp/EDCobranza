package com.arleckk.edcobranza.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.arleckk.edcobranza.global.Cifrado;
import com.arleckk.edcobranza.global.Constants;
import com.arleckk.edcobranza.ui.GestorActivity;

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
 * Created by arleckk on 7/23/16.
 */
public class DoLoginTask extends AsyncTask<String,Void,JSONObject> {

    private Context context;
    private String user;
    private String pwd;
    private ProgressDialog mProgress;

    public DoLoginTask(Context context, ProgressDialog mProgress) {
        this.context = context;
        this.mProgress = mProgress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setMessage("Espere");
        mProgress.setIndeterminate(true);
        mProgress.show();
        mProgress.setCancelable(false);
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(Constants.DO_LOGIN);
        String result = null;
        JSONObject json = null;

        try {

            List<NameValuePair> paramsPost = new ArrayList<NameValuePair>();
            user = params[0];
            pwd = params[0];
            paramsPost.add(new BasicNameValuePair("user",params[0]));
            paramsPost.add(new BasicNameValuePair("pwd",params[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(paramsPost));

            HttpResponse response = httpClient.execute(httpPost);

            result = EntityUtils.toString(response.getEntity());
//            Log.v("login_debug","result entity: "+result);
            json = new JSONObject(result);
        } catch(IOException e) {
            mProgress.dismiss();
            e.printStackTrace();
        } catch (JSONException e) {
            mProgress.dismiss();
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);

        mProgress.dismiss();
        try {
            if (json != null) {
                //el login fue exitoso
                if (json.get("estatus").toString().equals("1")) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("preferencias", context.MODE_PRIVATE);
                    final SharedPreferences.Editor editor = sharedPreferences.edit();
                    Cifrado cifrado = new Cifrado();
                    editor.putString("user",cifrado.encrypt(user));
                    editor.putString("pwd",cifrado.encrypt(pwd));
                    editor.commit();
                    Toast.makeText(context,String.valueOf(json.get("msg")),Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, GestorActivity.class));
                    ((Activity) context).finish();
                }
                //error con usuario o contraseña
                else {
                    Toast.makeText(context,String.valueOf(json.get("msg")),Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Ocurrio un error intentelo de nuevo mas tarde", Toast.LENGTH_LONG).show();
            }
        } catch(JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
