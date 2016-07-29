package com.arleckk.edcobranza.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arleckk.edcobranza.R;
import com.arleckk.edcobranza.global.Constants;
import com.arleckk.edcobranza.global.Utilities;
import com.arleckk.edcobranza.model.TrabajadorFonacot;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrabajadorFonacotFragment extends Fragment {

    private View view;
    private Activity activity;
    private TextView mAsignacion, mFechaAsignacion, mNoTrabajador, mTelefono, mTelefonoTrabajo, mTelefonoExtraUno, mTelefonoExtraDos,
            mSaldoActual, mFechaEjercidaCredito, mSucursalEjercimiento, mPagoMensual, mPlazo, mRFC, mNSS, mEmail;
    private Button mBtnTelefono, mBtnTelefonoTrabajo, mBtnTelefonoExtraUno, mBtnTelefonoExtraDos;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(activity != null) {
            activity = getActivity();
        }

        if(view == null) {
            view = inflater.inflate(R.layout.fragment_trabajador_fonacot,container,false);
        }

        mAsignacion = (TextView) view.findViewById(R.id.asignacion);
        mFechaAsignacion = (TextView) view.findViewById(R.id.fecha_asignacion);
        mNoTrabajador = (TextView) view.findViewById(R.id.num_trabajador);
        mTelefono = (TextView) view.findViewById(R.id.telefono);
        mTelefonoTrabajo = (TextView) view.findViewById(R.id.telefono_trabajo);
        mTelefonoExtraUno = (TextView) view.findViewById(R.id.telefono_extra_uno);
        mTelefonoExtraDos = (TextView) view.findViewById(R.id.telefono_extra_dos);
        mSaldoActual = (TextView) view.findViewById(R.id.saldo_actual);
        mFechaEjercidaCredito = (TextView) view.findViewById(R.id.fecha_ejercida_credito);
        mSucursalEjercimiento = (TextView) view.findViewById(R.id.sucursal_ejercimiento);
        mPagoMensual = (TextView) view.findViewById(R.id.pago_mensual);
        mPlazo = (TextView) view.findViewById(R.id.plazo);
        mRFC = (TextView) view.findViewById(R.id.rfc);
        mNSS = (TextView) view.findViewById(R.id.nss);
        mEmail = (TextView) view.findViewById(R.id.email);

        mBtnTelefono = (Button) view.findViewById(R.id.btn_telefono);
        mBtnTelefonoTrabajo = (Button) view.findViewById(R.id.btn_telefono_trabajo);
        mBtnTelefonoExtraUno = (Button) view.findViewById(R.id.btn_telefono_extra_uno);
        mBtnTelefonoExtraDos = (Button) view.findViewById(R.id.btn_telefono_extra_dos);

        GestorActivity.onChangeSpinnerTrabajadorFonacot = new GestorActivity.OnChangeSpinnerFonacot() {
            @Override
            public void onChangeSpinner(Object object) {
                if(object != null) {
                    TrabajadorFonacot trabajador = (TrabajadorFonacot) object;
                    if(trabajador != null) {
                        showTrabajador(trabajador);
                    } else {
                        Toast.makeText(view.getContext(),"No se encontro el trabajador",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        };
        return view;
    }

    public void showTrabajador(final TrabajadorFonacot trabajador) {
        mAsignacion.setText(trabajador.getAsignacion());
        mFechaAsignacion.setText(trabajador.getFechaAsignacion());
        mNoTrabajador.setText(trabajador.getNumTrabajador());
        mTelefono.setText(trabajador.getTelTrabajador());
        mTelefonoTrabajo.setText(trabajador.getTelefonoTrabajo());
        mTelefonoExtraUno.setText(trabajador.getTelExtraUno());
        mTelefonoExtraDos.setText(trabajador.getTelExtraDos());
        mSaldoActual.setText(trabajador.getSaldoActual());
        mFechaEjercidaCredito.setText(trabajador.getFechaEjercidaCredito());
        mSucursalEjercimiento.setText(trabajador.getSucursalEjercimiento());
        mPagoMensual.setText(trabajador.getPagoMensual());
        mPlazo.setText(trabajador.getPlazo());
        mRFC.setText(trabajador.getRfc());
        mNSS.setText(trabajador.getNss());
        mEmail.setText(trabajador.getEmail());

        //eventos
        mBtnTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utilities.isValidNumber(trabajador.getTelTrabajador())) {
                    startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mTelefono.getText().toString())));
                } else {
                    Toast.makeText(view.getContext(),"El número no es valido",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtnTelefonoTrabajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utilities.isValidNumber(trabajador.getTelefonoTrabajo())) {
                    startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mTelefonoTrabajo.getText().toString())));
                } else {
                    Toast.makeText(view.getContext(),"El número no es valido",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtnTelefonoExtraUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utilities.isValidNumber(trabajador.getTelExtraUno())) {
                    startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mTelefonoExtraUno.getText().toString())));
                } else {
                    Toast.makeText(view.getContext(),"El número no es valido",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtnTelefonoExtraDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utilities.isValidNumber(trabajador.getTelExtraDos())) {
                    startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+mTelefonoExtraDos.getText().toString())));
                } else {
                    Toast.makeText(view.getContext(),"El número no es valido",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
