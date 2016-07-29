package com.arleckk.edcobranza.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.arleckk.edcobranza.global.Cifrado;
import com.arleckk.edcobranza.R;
import com.arleckk.edcobranza.task.DoLoginTask;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private Button mLogin;
    private EditText mUser;
    private EditText mPwd;
//    private Spinner mCampana;
    private ProgressDialog mProgress;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogin = (Button) findViewById(R.id.btn_log_in);
        mUser = (EditText) findViewById(R.id.txt_user);
        mPwd = (EditText) findViewById(R.id.txt_password);
//        mCampana = (Spinner) findViewById(R.id.spinner_campanas);
        mProgress = new ProgressDialog(this);

        sharedPreferences = getSharedPreferences("preferencias",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final String user = sharedPreferences.getString("user","null");
        final String pwd = sharedPreferences.getString("pwd","null");

        if(!user.equals("null") && !pwd.equals("null")) {
            Log.v("Login",sharedPreferences.getString("user","null"));
            Log.v("Login",sharedPreferences.getString("pwd","null"));
            startActivity(new Intent(MainActivity.this, GestorActivity.class));
            finish();
        }

        mLogin.setOnClickListener(new View.OnClickListener() {

            Cifrado cifrado = new Cifrado();

            @Override
            public void onClick(View v) {
                String[] params = {mUser.getText().toString(),mPwd.getText().toString()};
                DoLoginTask login = new DoLoginTask(MainActivity.this, mProgress);
                login.execute(params);
            }
        });

    }
}
