package com.arleckk.edcobranza.ui;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.arleckk.edcobranza.R;
import com.arleckk.edcobranza.broadcast.UpdateLocationBroadCast;
import com.arleckk.edcobranza.global.Cifrado;
import com.arleckk.edcobranza.global.Constants;
import com.arleckk.edcobranza.global.Singleton;
import com.arleckk.edcobranza.global.Utilities;
import com.arleckk.edcobranza.global.VolleyEverywhere;
import com.arleckk.edcobranza.model.ReferenciaFonacot;
import com.arleckk.edcobranza.model.Tipificacion;
import com.arleckk.edcobranza.model.TrabajadorFonacot;
import com.arleckk.edcobranza.services.UpdateLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GestorActivity extends AppCompatActivity {

    interface OnChangeSpinnerFonacot {
        void onChangeSpinner(Object object);
    }

    interface OnChangeSpinnerReferenciaFonacot {
        void onChangeSpinner(Object object);
    }

    interface OnChangeSpinnerMaps {
        void onChangeSpinner(Object object);
    }

    interface OnPhotoTaken {
        void onPhotoTaken(Object object);
    }

    interface OnChangeSpinnerTipificacion {
        void onChangeSpinner(Object object);
    }

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    SharedPreferences sharedPreferences;
    private ProgressDialog mProgressDialog;
    private Uri fileUri;
    private Spinner mAccounts;
    private BroadcastReceiver myBroadcast;
    private IntentFilter intentFilter;
    public static OnChangeSpinnerFonacot onChangeSpinnerTrabajadorFonacot;
    public static OnChangeSpinnerReferenciaFonacot onChangeSpinnerReferenciaFonacot;
    public static OnChangeSpinnerMaps onChangeSpinnerMaps;
    public static OnPhotoTaken onPhotoTaken;
    public static OnChangeSpinnerTipificacion onChangeSpinnerTipificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestor);

        //configure broadcast
//        myBroadcast = new UpdateLocationBroadCast();
//        intentFilter = new IntentFilter("UPDATE_MY_LOCATION");

        mAccounts = (Spinner) findViewById(R.id.spinner_accounts);

        try {
            loadAccounts();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.menu_drawer) ;

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragmentFonacot()).commit();

        sharedPreferences = getSharedPreferences("preferencias",MODE_PRIVATE);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_item_update) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new TabFragmentFonacot()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_item_picture) {
//                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
//                    xfragmentTransaction.replace(R.id.containerView,new TabFragmentFonacot()).commit();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,100);
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new TakePhotoFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_item_log_off) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user",null);
                    editor.putString("pwd",null);
                    editor.commit();
                    startActivity(new Intent(GestorActivity.this, MainActivity.class));
                    finish();
                }

                return false;
            }

        });

        mAccounts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = mAccounts.getItemAtPosition(position).toString();

                Utilities.account = item;

                if (item != null) {
                    if (!item.equals("")) {
                        loadTrabajador(item);
                    } else {
                        Toast.makeText(GestorActivity.this, "Ocurrio un error con los datos",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GestorActivity.this, "Ocurrio un error con los datos",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

//        startService(new Intent(this,UpdateLocation.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
//        registerReceiver(myBroadcast,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    //resultado camara
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if(onPhotoTaken != null) {
                onPhotoTaken.onPhotoTaken(imageBitmap);
            } else {
                Log.v("camera_debug","null");
            }
        }
    }

    public void loadAccounts() throws UnsupportedEncodingException {
        VolleyEverywhere.getInstance(getApplicationContext()).
                addToRequestQueue(new JsonArrayRequest(
                        Request.Method.GET,
                        Constants.GET_FONACOT_ACCOUNTS_BY_USER + "?" + "user="+new Cifrado().decrypt(
                                getSharedPreferences("preferencias",MODE_PRIVATE).getString("user","null")),
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                //what do to with data
                                getAccounts(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("volley_debug", "Error Volley: "+ error.getMessage());
                    }
                }
                ));
    }

    private void getAccounts(JSONArray response) {
        String[] accounts = new String[response.length()];
        try {
            for (int i = 0; i < response.length(); i++) {
                accounts[i] = response.getJSONObject(i).getString("credito");
            }
        }  catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("account_debug","accs: "+response.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,accounts);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mAccounts.setAdapter(adapter);
//        mAccounts.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,accounts));
        mAccounts.setSelection(0,true);
    }

    //aqui se ejecutan todas las interfaces internas (funcionalidad de los fragments)
    private void loadTrabajador(final String credito) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Cargando información");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        VolleyEverywhere.getInstance(getApplicationContext()).
                addToRequestQueue(new JsonArrayRequest(
                        Request.Method.GET,
                        Constants.GET_FONACOT_TRABAJADOR_BY_ID + "?" + "credito="+credito,
                        null,
                        new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
//                                Log.v("general_debug","cuenta general: "+response.toString());
                                TrabajadorFonacot trabajador = getTrabajador(response);
                                //cargar trabajador fonacot
                                if (trabajador != null) {
                                    if(onChangeSpinnerTrabajadorFonacot != null) {
                                        onChangeSpinnerTrabajadorFonacot.onChangeSpinner(trabajador);
                                    }
                                }

                                if(onChangeSpinnerMaps != null) {
                                    Log.v("volley_debug","maps");
                                    onChangeSpinnerMaps.onChangeSpinner(trabajador);
                                }

                                ReferenciaFonacot referencia = getReferencia(response);
                                //cargar referencia fonacot
                                if(onChangeSpinnerReferenciaFonacot != null) {
                                    onChangeSpinnerReferenciaFonacot.onChangeSpinner(referencia);
                                }

                                ArrayList<Tipificacion> tipificaciones = getTipificaciones(response);
                                if(onChangeSpinnerTipificacion != null) {
                                    onChangeSpinnerTipificacion.onChangeSpinner(tipificaciones);
                                }

                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("volley_debug", "Error Volley: "+ error.getMessage());
                        mProgressDialog.dismiss();
                    }
                }
                ));

        mProgressDialog.dismiss();

    }

    private TrabajadorFonacot getTrabajador(JSONArray response) {
        TrabajadorFonacot trabajadorFonacot = null;
        try {
            trabajadorFonacot = new TrabajadorFonacot(response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("credito"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("usuario"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("asignacion"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("fecha_asignacion"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("num_trabajador"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("nombre_trabajador"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("tel_trabajador"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("tipo"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("calle"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("delegacion"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("estado"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("codigo_postal"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("monto_inicial"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("saldo_actual"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("fecha_ejercida_credito"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("sucursal_ejercimiento"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("pago_mensual"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("plazo"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("telefono_trabajo"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("rfc"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("num_seg_social"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("email"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("tel_extra_uno"),
                    response.getJSONObject(0).getJSONArray("trabajador").getJSONObject(0).getString("tel_extra_dos"));
            return trabajadorFonacot;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trabajadorFonacot;
    }

    private ReferenciaFonacot getReferencia(JSONArray response){
        ReferenciaFonacot referencia = null;

        try {
            referencia = new ReferenciaFonacot(response.getJSONObject(0).getJSONArray("referencia").getJSONObject(0).getString("nombre_ref_uno"),
                    response.getJSONObject(0).getJSONArray("referencia").getJSONObject(0).getString("tel_ref_uno"),"","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return referencia;
    }

    private ArrayList<Tipificacion> getTipificaciones(JSONArray response) {
        ArrayList<Tipificacion> tipificaciones = null;
        try {
            tipificaciones = new ArrayList<Tipificacion>();
            JSONArray tipificacionesJSON = response.getJSONObject(0).getJSONArray("tipificacion");
            for(int i = 0; i < tipificacionesJSON.length(); i++){
                JSONObject json = tipificacionesJSON.getJSONObject(i);
                tipificaciones.add(new Tipificacion(json.getString("id"),
                        json.getString("credito_trabajador"),
                        json.getString("usuario"),
                        json.getString("estatus"),
                        json.getString("subestatus"),
                        json.getString("fecha_gestion"),
                        json.getString("comentario"),
                        json.getString("monto_pago"),
                        json.getString("fecha_pago"),
                        json.getString("porc_quita"),
                        json.getString("fecha_dev"),
                        json.getString("lugar_dev"),
                        json.getString("monto_rees"),
                        json.getString("plazo_rees"),
                        json.getString("fecha_visita"),
                        json.getString("desc_ofrecido")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tipificaciones;
    }

}
