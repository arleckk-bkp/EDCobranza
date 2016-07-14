package com.arleckk.edcobranza.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arleckk.edcobranza.R;

/**
 * Created by arleckk on 7/14/16.
 */
public class ReferenciaFonacotFragment extends Fragment {

    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_trabajador_fonacot,container,false);
        }

        return view;
    }
}
