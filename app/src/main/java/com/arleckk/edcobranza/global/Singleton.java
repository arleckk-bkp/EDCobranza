package com.arleckk.edcobranza.global;

import com.arleckk.edcobranza.model.TrabajadorFonacot;

/**
 * Created by arleckk on 12/07/16.
 */
public class Singleton {

    private static Singleton instance = null;
    public static TrabajadorFonacot trabajadorFonacot;

    private Singleton() {

    }

    public static Singleton getInstance() {

        if(instance == null) {
            instance = new Singleton();
        }

        return instance;
    }



}
