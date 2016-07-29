package com.arleckk.edcobranza.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arleckk.edcobranza.R;
import com.arleckk.edcobranza.model.ReferenciaFonacot;
import com.arleckk.edcobranza.model.TrabajadorFonacot;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by arleckk on 7/14/16.
 */
public class ReferenciaFonacotFragment extends Fragment {

    private View view;
    private TextView mNombreRefUno,mTelRefUno,mNombreRefDos,mTelRefDos;
    private Button mBtnTelRefUno, mBtnTelRefDos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_referencia_fonacot,container,false);
        }

        mNombreRefUno = (TextView) view.findViewById(R.id.nombre_ref_uno_fonacot);
        mTelRefUno = (TextView) view.findViewById(R.id.tel_ref_uno_fonacot);
        mNombreRefDos = (TextView) view.findViewById(R.id.nombre_ref_dos_fonacot);
        mTelRefDos = (TextView) view.findViewById(R.id.tel_ref_dos_fonacot);
        mBtnTelRefUno = (Button) view.findViewById(R.id.btn_tel_ref_uno_fonacot);
        mBtnTelRefDos = (Button) view.findViewById(R.id.btn_tel_ref_dos_fonacot);

        GestorActivity.onChangeSpinnerReferenciaFonacot = new GestorActivity.OnChangeSpinnerReferenciaFonacot() {
            @Override
            public void onChangeSpinner(Object object) {
                ReferenciaFonacot referencia = (ReferenciaFonacot) object;
                if(referencia != null) {
                    Log.v("volley_debug","referencia no null: "+referencia.toString());
                    showReferencia(referencia);
                } else {
                    Toast.makeText(view.getContext(),"No existen referencias",Toast.LENGTH_SHORT).show();
                    clearReferencia();
                }
            }
        };

        return view;
    }

    private void showReferencia(ReferenciaFonacot referencia) {
        mNombreRefUno.setText(referencia.getNombreRefUno());
        mTelRefUno.setText(referencia.getTelRefUno());
        mNombreRefDos.setText(referencia.getNombreRefDos());
        mTelRefDos.setText(referencia.getTelRefDos());
    }

    private void clearReferencia() {
        mNombreRefUno.setText("");
        mTelRefUno.setText("");
        mNombreRefDos.setText("");
        mTelRefDos.setText("");
    }

}
