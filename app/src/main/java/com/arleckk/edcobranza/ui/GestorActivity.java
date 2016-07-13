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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.arleckk.edcobranza.R;
import com.arleckk.edcobranza.global.Constants;
import com.arleckk.edcobranza.global.VolleyEverywhere;
import com.arleckk.edcobranza.services.UpdateLocation;

import org.json.JSONArray;
import org.json.JSONException;

public class GestorActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    SharedPreferences sharedPreferences;
    private Spinner mAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestor);

        loadAccounts();

        mAccounts = (Spinner) findViewById(R.id.spinner_accounts);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

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
        Log.v("volley_debug","response to string: "+response.toString());
        String[] accounts = new String[response.length()];
        try {
            for (int i = 0; i < response.length(); i++) {
                accounts[i] = response.getJSONObject(i).getString("credito");
            }
        }  catch (JSONException e) {
            e.printStackTrace();
        }
        mAccounts.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,accounts));
        Log.v("volley_debug","accounts: "+accounts.toString());
    }

}
