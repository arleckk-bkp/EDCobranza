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
import android.widget.DatePicker;
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
public class VisitaFragment extends Fragment {

    private View view;
    private EditText mFechaVisita, mComentario;
    private Context context;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Calendar myCalendar;
    private FloatingActionButton mFab;
    private Spinner mEstatus, mSubestatus;
    private ProgressDialog mProgress;

    public VisitaFragment(){

    }

    @SuppressLint("ValidFragment")
    public VisitaFragment(Context context, Spinner mEstatus, Spinner mSubestatus, ProgressDialog mProgress) {
        this.context = context;
        this.mEstatus = mEstatus;
        this.mSubestatus = mSubestatus;
        this.mProgress = mProgress;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_captura_visita, container, false);
        }

        mFechaVisita = (EditText) view.findViewById(R.id.fecha_visita);
        mComentario = (EditText) view.findViewById(R.id.comentario_visita);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab_visita);

        myCalendar = Calendar.getInstance();

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Utilities.updateLabel(mFechaVisita, myCalendar);
            }
        };

        mFechaVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, dateSetListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utilities.areValid(new String[] {
                        mFechaVisita.getText().toString(),
                        mComentario.getText().toString()
                })) {

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
                                mFechaVisita.getText().toString(),
                        };
                    } catch(UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    task.execute(params);
                } else {
                    Toast.makeText(context, "Uno de los campos esta vacio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
