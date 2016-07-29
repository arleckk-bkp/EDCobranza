package com.arleckk.edcobranza.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arleckk.edcobranza.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arleckk on 12/07/16.
 */
public class TabFragmentFonacot extends Fragment{

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int items = 5;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        //establece el numero maximo de vistas que estaran cargadas en memoria
        viewPager.setOffscreenPageLimit(5);
        setupViewPager(viewPager);
        /**
         *Set an Apater for the View Pager
         */
//        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        return x;
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new TrabajadorFonacotFragment(), "Trabajador");
        adapter.addFrag(new ReferenciaFonacotFragment(), "Referencia");
        adapter.addFrag(new TipificacionFonacotFragment(), "Tipificaciones");
        adapter.addFrag(new MapsFragment(), "Mapa");
        adapter.addFrag(new CapturaFragment(), "Capturar");
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new TrabajadorFonacotFragment();
                case 1 : return new ReferenciaFonacotFragment();
                case 2 : return new TipificacionFonacotFragment();
                case 3 : return new MapsFragment();
                case 4: return new CapturaFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return items;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Trabajador";
                case 1 :
                    return "Referencias";
                case 2 :
                    return "Tipificaciones";
                case 3:
                    return "Mapa";
                case 4:
                    return "Captura";
            }
            return null;
        }
    }

}
