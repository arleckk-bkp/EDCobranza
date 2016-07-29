package com.arleckk.edcobranza.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.arleckk.edcobranza.R;
import com.arleckk.edcobranza.global.Cifrado;
import com.arleckk.edcobranza.global.Utilities;
import com.arleckk.edcobranza.task.CaptureTask;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

/**
 * Created by arleckk on 7/27/16.
 */
public class PagadaFragment extends Fragment {

    private View view;
    private EditText mMontoPago,mComentario;
    private Context context;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Calendar myCalendar;
    private FloatingActionButton mFab;
    private Spinner mEstatus, mSubestatus;
    private ProgressDialog mProgress;

    public PagadaFragment(){

    }

    @SuppressLint("ValidFragment")
    public PagadaFragment(Context context, Spinner mEstatus, Spinner mSubestatus, ProgressDialog mProgress) {
        this.context = context;
        this.mEstatus = mEstatus;
        this.mSubestatus = mSubestatus;
        this.mProgress = mProgress;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_captura_pagada, container, false);
        }


        mMontoPago = (EditText) view.findViewById(R.id.monto_pago_pagada);
        mComentario = (EditText) view.findViewById(R.id.comentario_pagada);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab_pagada);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utilities.areValid(new String[]{
                        mMontoPago.getText().toString(),
                        mComentario.getText().toString()
                })){
                    CaptureTask task = new CaptureTask(getContext(), mProgress);
                    String[] params = null;
                    Cifrado cifrado = null;
                    SharedPreferences sharedPreferences = null;

                    try {
                        sharedPreferences = getActivity().getSharedPreferences("preferencias",getActivity().MODE_PRIVATE);
                        cifrado = new Cifrado();
                        params = new String[]{
                                mSubestatus.getSelectedItem().toString(),
                                Utilities.account,
                                cifrado.decrypt(sharedPreferences.getString("user", "null")),
                                mEstatus.getSelectedItem().toString(),
                                mSubestatus.getSelectedItem().toString(),
                                mComentario.getText().toString(),
                                mMontoPago.getText().toString()
                        };

                    } catch(UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    task.execute(params);
                } else{
                    Toast.makeText(context, "Uno de los campos esta vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
