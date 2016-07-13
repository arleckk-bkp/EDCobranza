package com.arleckk.edcobranza.global;

/**
 * Created by arleckk on 12/07/16.
 */
public class Singleton {

    private static Singleton instance = null;

    private Singleton() {

    }

    public static Singleton getInstance() {

        if(instance == null) {
            instance = new Singleton();
        }

        return instance;
    }

}
