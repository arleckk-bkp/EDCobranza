package com.arleckk.edcobranza.global;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by arleckk on 12/07/16.
 */
public class Cifrado {

    public Cifrado() {

    }

    public String encrypt(String text) throws UnsupportedEncodingException {
        byte[] data = text.getBytes("UTF-8");
        String textEncrypted = Base64.encodeToString(data, Base64.DEFAULT);
        return textEncrypted;
    }

    public String decrypt(String text) throws UnsupportedEncodingException {
        byte[] data = Base64.decode(text, Base64.DEFAULT);
        String textDecrypted = new String(data, "UTF-8");
        return textDecrypted;
    }

}
