package com.arleckk.edcobranza.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.arleckk.edcobranza.R;
import com.arleckk.edcobranza.task.GetEstatusTask;
import com.arleckk.edcobranza.task.GetSubEstatusTask;

/**
 * Created by arleckk on 7/25/16.
 */
public class CapturaFragment extends Fragment {

    private View view;
    private Spinner mEstatus, mSubestatus;
    private ProgressDialog mProgress;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view == null) {
            view = inflater.inflate(R.layout.fragment_captura,container,false);
        }
        mProgress = new ProgressDialog(getContext());
        mEstatus = (Spinner) view.findViewById(R.id.spinner_estatus);
        mSubestatus = (Spinner) view.findViewById(R.id.spinner_subestatus);

        mFragmentManager = getChildFragmentManager();

        GetEstatusTask estatusTask = new GetEstatusTask(getContext(), mProgress, mEstatus);
        estatusTask.execute();

        mEstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String estatus = mEstatus.getSelectedItem().toString();
                estatus = estatus.replace(" ","%20");
                new GetSubEstatusTask(getContext(), mProgress, mSubestatus).execute(estatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSubestatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String subestatus = mSubestatus.getSelectedItem().toString();

                switch(subestatus) {
                    case "Promesa de Pago Parcial":
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.container_captura, new PromesaPagoFragment(getContext(),
                                mEstatus, mSubestatus, mProgress)).commit();
                        break;

                    case "Promesa de pago":
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.container_captura, new PromesaPagoFragment(getContext(),
                                mEstatus, mSubestatus, mProgress)).commit();

                        break;

                    case "Cuenta con convenio":
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.container_captura, new CuentaConvenioFragment(getContext(),
                                mEstatus, mSubestatus, mProgress)).commit();

                        break;

                    case "Propuesta de Devolucion del bien en garantia u otro":
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.container_captura, new PropuestaDevolucionFragment(getContext(),
                                mEstatus, mSubestatus, mProgress)).commit();

                        break;

                    case "Reestructura":
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.container_captura, new ReestructuraFragment(getContext(),
                                mEstatus, mSubestatus, mProgress)).commit();

                        break;

                    case "Realizar Visita":
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.container_captura, new VisitaFragment(getContext(),
                                mEstatus, mSubestatus, mProgress)).commit();
                        break;

                    case "Pagada":
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.container_captura, new PagadaFragment(getContext(),
                                mEstatus, mSubestatus, mProgress)).commit();
                        break;

                    case "Saldo para Bonificar":
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.container_captura, new BonificarFragment(getContext(),
                                mEstatus, mSubestatus, mProgress)).commit();
                        break;

                    default:
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.container_captura, new CapturaGeneralFragment(getContext(),
                                mEstatus, mSubestatus, mProgress)).commit();
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}
