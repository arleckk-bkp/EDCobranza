package com.arleckk.edcobranza.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;
import com.android.internal.http.multipart.Part;
import com.arleckk.edcobranza.global.Cifrado;
import com.arleckk.edcobranza.global.Constants;
import com.arleckk.edcobranza.global.Utilities;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arleckk on 7/21/16.
 */
public class UploadPhotoTask extends AsyncTask<Bitmap,Void,JSONObject> {

    private Context context;
    private ProgressDialog mProgress;

    public UploadPhotoTask (Context context, ProgressDialog mProgress) {
        this.context = context;
        this.mProgress = mProgress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgress = new ProgressDialog(context);
        mProgress.setMessage("Subiendo archivo por favor espere");
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    protected JSONObject doInBackground(Bitmap... params) {

        String result = null;
        JSONObject json = null;
        if (params[0] == null) {
            return null;
        }

        Bitmap bitmap = params[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
        InputStream is = new ByteArrayInputStream(stream.toByteArray());
        byte[] bytes = stream.toByteArray();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = null;
        String fileName = "FONACOT"+"-"+Utilities.getDatePhoto()+"-"+System.currentTimeMillis()+".jpg";
        try {
            HttpPost httpPost = new HttpPost(Constants.POST_PHOTO);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            Log.v("photo_debug",fileName);
            builder.addPart("myFile", new ByteArrayBody(bytes,fileName));
//            builder.addPart("myFile", new ByteArrayBody(bytes,"FONACOT"+ Utilities.getDate()+Utilities.getHour()+".jpg"));
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            Log.v("photo_debug",response.getStatusLine().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            HttpPost httpPost = new HttpPost(Constants.POST_PHOTO_BD);
            List<NameValuePair> paramsPost = new ArrayList<NameValuePair>();
            SharedPreferences sharedPreferences = context.getSharedPreferences("preferencias",context.MODE_PRIVATE);
            Cifrado cifrado = new Cifrado();
            paramsPost.add(new BasicNameValuePair("usuario",cifrado.decrypt(sharedPreferences.getString("user","null"))));
            paramsPost.add(new BasicNameValuePair("archivo",fileName));
            httpPost.setEntity(new UrlEncodedFormEntity(paramsPost));
            response = httpClient.execute(httpPost);
            Log.v("photo_debug",response.getStatusLine().toString());
            if(response != null) {
                result = EntityUtils.toString(response.getEntity());
                Log.v("photo_debug",result);
                json = new JSONObject(result);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            is.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;

    }

    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);
        mProgress.dismiss();
        if(json != null) {
            try {
                Toast.makeText(context,json.getString("msg"),Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context,"Ocurrio un error al subir el archivo",Toast.LENGTH_SHORT).show();
        }

    }



}
