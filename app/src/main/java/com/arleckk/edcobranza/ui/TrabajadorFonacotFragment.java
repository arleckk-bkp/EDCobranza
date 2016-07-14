package com.arleckk.edcobranza.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arleckk.edcobranza.R;
import com.arleckk.edcobranza.model.TrabajadorFonacot;

public class TrabajadorFonacotFragment extends Fragment {

    private View view;
    private static TextView mAsignacion, mFechaAsignacion, mNoTrabajador, mTelefono, mTelefonoTrabajo, mTelefonoExtraUno, mTelefonoExtraDos,
            mSaldoActual, mFechaEjercidaCredito, mSucursalEjercimiento, mPagoMensual, mPlazo, mRFC, mNSS, mEmail;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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

        mAsignacion.setText("trabajador");
//        return inflater.inflate(R.layout.fragment_trabajador_fonacot,null);
        return view;
    }

    public static void showTrabajador(TrabajadorFonacot trabajador) {
        Log.v("volley_debug","fragment"+trabajador.toString());
        mAsignacion.setText(trabajador.getAsignacion());
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
    }

}
