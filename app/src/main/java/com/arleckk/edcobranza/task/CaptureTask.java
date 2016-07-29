package com.arleckk.edcobranza.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.arleckk.edcobranza.global.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arleckk on 7/27/16.
 */
public class CaptureTask extends AsyncTask<String, Void, JSONObject> {

    private Context context;
    private ProgressDialog mProgress;

    public CaptureTask(Context context, ProgressDialog mProgress) {
        this.context = context;
        this.mProgress = mProgress;
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
    protected JSONObject doInBackground(String... params) {
        String result = null;
        JSONObject json = null;
        String type = params[0];
        String url = null;
        List<NameValuePair> paramsPost = null;
        try {
            HttpClient client = new DefaultHttpClient();

            switch(type) {
                case "Promesa de Pago Parcial":
                    url = Constants.POST_CAPTURA_PROMESA;
                    paramsPost = new ArrayList<NameValuePair>();
                    paramsPost.add(new BasicNameValuePair("credito",params[1]));
                    paramsPost.add(new BasicNameValuePair("usuario",params[2]));
                    paramsPost.add(new BasicNameValuePair("estatus",params[3]));
                    paramsPost.add(new BasicNameValuePair("subestatus",params[4]));
                    paramsPost.add(new BasicNameValuePair("comentario",params[5]));
                    paramsPost.add(new BasicNameValuePair("monto_pago",params[6]));
                    paramsPost.add(new BasicNameValuePair("fecha_pago",params[7]));

                    break;
                case "Promesa de pago":
                    url = Constants.POST_CAPTURA_PROMESA;
                    paramsPost = new ArrayList<NameValuePair>();
                    paramsPost.add(new BasicNameValuePair("credito",params[1]));
                    paramsPost.add(new BasicNameValuePair("usuario",params[2]));
                    paramsPost.add(new BasicNameValuePair("estatus",params[3]));
                    paramsPost.add(new BasicNameValuePair("subestatus",params[4]));
                    paramsPost.add(new BasicNameValuePair("comentario",params[5]));
                    paramsPost.add(new BasicNameValuePair("monto_pago",params[6]));
                    paramsPost.add(new BasicNameValuePair("fecha_pago",params[7]));

                    break;
                case "Cuenta con convenio":
                    url = Constants.POST_CAPTURA_CONVENIO;
                    paramsPost = new ArrayList<NameValuePair>();
                    paramsPost.add(new BasicNameValuePair("credito",params[1]));
                    paramsPost.add(new BasicNameValuePair("usuario",params[2]));
                    paramsPost.add(new BasicNameValuePair("estatus",params[3]));
                    paramsPost.add(new BasicNameValuePair("subestatus",params[4]));
                    paramsPost.add(new BasicNameValuePair("comentario",params[5]));
                    paramsPost.add(new BasicNameValuePair("monto_pago",params[6]));
                    paramsPost.add(new BasicNameValuePair("fecha_pago",params[7]));
                    paramsPost.add(new BasicNameValuePair("porc_quita",params[8]));

                    break;
                case "Propuesta de Devolucion del bien en garantia u otro":
                    url = Constants.POST_CAPTURA_DEVOLUCION;
                    paramsPost = new ArrayList<NameValuePair>();
                    paramsPost.add(new BasicNameValuePair("credito",params[1]));
                    paramsPost.add(new BasicNameValuePair("usuario",params[2]));
                    paramsPost.add(new BasicNameValuePair("estatus",params[3]));
                    paramsPost.add(new BasicNameValuePair("subestatus",params[4]));
                    paramsPost.add(new BasicNameValuePair("comentario",params[5]));
                    paramsPost.add(new BasicNameValuePair("fecha_dev",params[6]));
                    paramsPost.add(new BasicNameValuePair("lugar_dev",params[7]));

                    break;
                case "Reestructura":
                    url = Constants.POST_CAPTURA_REESTRUCTURA;
                    paramsPost = new ArrayList<NameValuePair>();
                    paramsPost.add(new BasicNameValuePair("credito",params[1]));
                    paramsPost.add(new BasicNameValuePair("usuario",params[2]));
                    paramsPost.add(new BasicNameValuePair("estatus",params[3]));
                    paramsPost.add(new BasicNameValuePair("subestatus",params[4]));
                    paramsPost.add(new BasicNameValuePair("comentario",params[5]));
                    paramsPost.add(new BasicNameValuePair("monto_rees",params[6]));
                    paramsPost.add(new BasicNameValuePair("plazo_rees",params[7]));
                    paramsPost.add(new BasicNameValuePair("fecha_pago",params[8]));

                    break;
                case "Realizar Visita":
                    url = Constants.POST_CAPTURA_VISITA;
                    paramsPost = new ArrayList<NameValuePair>();
                    paramsPost.add(new BasicNameValuePair("credito",params[1]));
                    paramsPost.add(new BasicNameValuePair("usuario",params[2]));
                    paramsPost.add(new BasicNameValuePair("estatus",params[3]));
                    paramsPost.add(new BasicNameValuePair("subestatus",params[4]));
                    paramsPost.add(new BasicNameValuePair("comentario",params[5]));
                    paramsPost.add(new BasicNameValuePair("fecha_visita",params[6]));

                    break;
                case "Pagada":
                    url = Constants.POST_CAPTURA_PAGADA;
                    paramsPost = new ArrayList<NameValuePair>();
                    paramsPost.add(new BasicNameValuePair("credito",params[1]));
                    paramsPost.add(new BasicNameValuePair("usuario",params[2]));
                    paramsPost.add(new BasicNameValuePair("estatus",params[3]));
                    paramsPost.add(new BasicNameValuePair("subestatus",params[4]));
                    paramsPost.add(new BasicNameValuePair("comentario",params[5]));
                    paramsPost.add(new BasicNameValuePair("monto_pago",params[6]));

                    break;
                case "Saldo para Bonificar":
                    url = Constants.POST_CAPTURA_BONIFICAR;
                    paramsPost = new ArrayList<NameValuePair>();
                    paramsPost.add(new BasicNameValuePair("credito",params[1]));
                    paramsPost.add(new BasicNameValuePair("usuario",params[2]));
                    paramsPost.add(new BasicNameValuePair("estatus",params[3]));
                    paramsPost.add(new BasicNameValuePair("subestatus",params[4]));
                    paramsPost.add(new BasicNameValuePair("comentario",params[5]));
                    paramsPost.add(new BasicNameValuePair("monto_pago",params[6]));
                    paramsPost.add(new BasicNameValuePair("desc_ofrecido",params[7]));

                    break;
                default:
                    url = Constants.POST_CAPTURA_GENERAL;
                    paramsPost = new ArrayList<NameValuePair>();
                    paramsPost.add(new BasicNameValuePair("credito",params[1]));
                    paramsPost.add(new BasicNameValuePair("usuario",params[2]));
                    paramsPost.add(new BasicNameValuePair("estatus",params[3]));
                    paramsPost.add(new BasicNameValuePair("subestatus",params[4]));
                    paramsPost.add(new BasicNameValuePair("comentario",params[5]));

                    break;
            }

            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(paramsPost));
            HttpResponse response = client.execute(post);

            result = EntityUtils.toString(response.getEntity());
            json = new JSONObject(result);

        } catch(IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("captura_debug","resultado captura: "+result);

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);
        if(mProgress.isShowing()) {
            mProgress.dismiss();
        }
        try {
            if(json != null) {
                Toast.makeText(context, json.getString("msg"), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Ocurrio un error, intente de nuevo mas tarde", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
