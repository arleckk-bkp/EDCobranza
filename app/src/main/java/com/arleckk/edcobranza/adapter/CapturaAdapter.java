package com.arleckk.edcobranza.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arleckk.edcobranza.R;
import com.arleckk.edcobranza.model.Tipificacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arleckk on 7/21/16.
 */
public class CapturaAdapter extends ArrayAdapter<Tipificacion> {

    private View view;

    public CapturaAdapter(Context context, ArrayList<Tipificacion> tipificaciones) {
//        super(context,0,tipificaciones);
        super(context,R.layout.list_tipificaciones, tipificaciones);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view =  convertView;
        if(view == null) {
            view = inflater.inflate(R.layout.list_tipificaciones,parent,false);
        }

        if (view != null) {

            TextView usuarioCaptura = (TextView) view.findViewById(R.id.usuario_captura);
            TextView fechaCaptura = (TextView) view.findViewById(R.id.fecha_captura);
            TextView estatusCaptura = (TextView) view.findViewById(R.id.estatus_captura);
            TextView subestatusCaptura = (TextView) view.findViewById(R.id.subestatus_captura);
            TextView comentarioCaptura = (TextView) view.findViewById(R.id.comentario_captura);

            Tipificacion tipificacion = getItem(position);

            usuarioCaptura.setText(tipificacion.getUsuario());
            fechaCaptura.setText(tipificacion.getFechaGestion());
            estatusCaptura.setText(tipificacion.getEstatus());
            subestatusCaptura.setText(tipificacion.getSubestatus());
            comentarioCaptura.setText(tipificacion.getComentario());

        }

        return view;
    }
}
