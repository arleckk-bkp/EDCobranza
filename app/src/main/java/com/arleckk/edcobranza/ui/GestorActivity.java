package com.arleckk.edcobranza.ui;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.arleckk.edcobranza.R;
import com.arleckk.edcobranza.global.Constants;
import com.arleckk.edcobranza.global.Singleton;
import com.arleckk.edcobranza.global.VolleyEverywhere;
import com.arleckk.edcobranza.model.TrabajadorFonacot;
import com.arleckk.edcobranza.services.UpdateLocation;

import org.json.JSONArray;
import org.json.JSONException;

public class GestorActivity extends AppCompatActivity {

    interface OnChangeSpinner {
        void onChangeSpinner(Object object);
    }

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    SharedPreferences sharedPreferences;
    private Spinner mAccounts;
    public static OnChangeSpinner onChangeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestor);

        mAccounts = (Spinner) findViewById(R.id.spinner_accounts);

        loadAccounts();

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
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new TabFragmentFonacot()).commit();
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
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        startService(new Intent(this,UpdateLocation.class));

    }

    public void loadAccounts() {
        VolleyEverywhere.getInstance(getApplicationContext()).
                addToRequestQueue(new JsonArrayRequest(
                        Request.Method.GET,
                        Constants.GET_FONACOT_ACCOUNTS_BY_USER + "?" + "user=OSCAR.FLORES",
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
        mAccounts.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,accounts));
        mAccounts.setSelection(0,true);
    }

    private void loadTrabajador(String credito) {
        VolleyEverywhere.getInstance(getApplicationContext()).
                addToRequestQueue(new JsonArrayRequest(
                        Request.Method.GET,
                        Constants.GET_FONACOT_TRABAJADOR_BY_ID + "?" + "credito="+credito,
                        null,
                        new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                TrabajadorFonacot trabajador = getTrabajador(response);
                                if (trabajador != null) {
                                    if(onChangeSpinner != null) {
                                        onChangeSpinner.onChangeSpinner(trabajador);
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("volley_debug", "Error Volley: "+ error.getMessage());
                    }
                }
                ));
    }

    private TrabajadorFonacot getTrabajador(JSONArray response) {
        TrabajadorFonacot trabajadorFonacot = null;
        try {
            trabajadorFonacot = new TrabajadorFonacot(response.getJSONObject(0).getString("credito"),
                    response.getJSONObject(0).getString("usuario"), response.getJSONObject(0).getString("asignacion"),
                    response.getJSONObject(0).getString("fecha_asignacion"),response.getJSONObject(0).getString("num_trabajador"),
                    response.getJSONObject(0).getString("nombre_trabajador"),response.getJSONObject(0).getString("tel_trabajador"),
                    response.getJSONObject(0).getString("tipo"),response.getJSONObject(0).getString("calle"),
                    response.getJSONObject(0).getString("delegacion"),response.getJSONObject(0).getString("estado"),
                    response.getJSONObject(0).getString("codigo_postal"),response.getJSONObject(0).getString("monto_inicial"),
                    response.getJSONObject(0).getString("saldo_actual"),response.getJSONObject(0).getString("fecha_ejercida_credito"),
                    response.getJSONObject(0).getString("sucursal_ejercimiento"),response.getJSONObject(0).getString("pago_mensual"),
                    response.getJSONObject(0).getString("plazo"),response.getJSONObject(0).getString("telefono_trabajo"),
                    response.getJSONObject(0).getString("rfc"),response.getJSONObject(0).getString("num_seg_social"),
                    response.getJSONObject(0).getString("email"),response.getJSONObject(0).getString("tel_extra_uno"),
                    response.getJSONObject(0).getString("tel_extra_dos"));
            Log.v("volley_debug","getTrabajadorFromResponse: "+trabajadorFonacot.toString());
            return trabajadorFonacot;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trabajadorFonacot;
    }


}
