package com.arleckk.edcobranza.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.arleckk.edcobranza.R;
import com.arleckk.edcobranza.model.Tipificacion;
import com.arleckk.edcobranza.task.CreateListTipificacionesTask;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by arleckk on 7/15/16.
 */
public class TipificacionFonacotFragment extends Fragment {

    private View view;
    private ListView mTipificaciones;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_tipificacion_fonacot,container,false);
        }

        mTipificaciones = (ListView) view.findViewById(R.id.list_tipificaciones);

        GestorActivity.onChangeSpinnerTipificacion = new GestorActivity.OnChangeSpinnerTipificacion() {
            @Override
            public void onChangeSpinner(Object object) {
                CreateListTipificacionesTask task = new CreateListTipificacionesTask(getContext(),mTipificaciones);
                ArrayList<Tipificacion> tipificaciones = (ArrayList<Tipificacion>) object;
                task.execute(tipificaciones);
            }
        };

        return view;
    }

}

