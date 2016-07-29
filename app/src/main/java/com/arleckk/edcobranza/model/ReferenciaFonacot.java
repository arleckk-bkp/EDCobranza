package com.arleckk.edcobranza.model;

/**
 * Created by arleckk on 7/15/16.
 */
public class ReferenciaFonacot {

    private String nombreRefUno;
    private String telRefUno;
    private String nombreRefDos;
    private String telRefDos;

    public ReferenciaFonacot(String nombreRefUno, String telRefUno, String nombreRefDos, String telRefDos) {
        this.nombreRefUno = nombreRefUno;
        this.telRefUno = telRefUno;
        this.nombreRefDos = nombreRefDos;
        this.telRefDos = telRefDos;
    }

    public String getNombreRefUno() {
        return nombreRefUno;
    }

    public void setNombreRefUno(String nombreRefUno) {
        this.nombreRefUno = nombreRefUno;
    }

    public String getTelRefUno() {
        return telRefUno;
    }

    public void setTelRefUno(String telRefUno) {
        this.telRefUno = telRefUno;
    }

    public String getNombreRefDos() {
        return nombreRefDos;
    }

    public void setNombreRefDos(String nombreRefDos) {
        this.nombreRefDos = nombreRefDos;
    }

    public String getTelRefDos() {
        return telRefDos;
    }

    public void setTelRefDos(String telRefDos) {
        this.telRefDos = telRefDos;
    }

    @Override
    public String toString() {
        return "ReferenciaFonacot{" +
                "nombreRefUno='" + nombreRefUno + '\'' +
                ", telRefUno='" + telRefUno + '\'' +
                ", nombreRefDos='" + nombreRefDos + '\'' +
                ", telRefDos='" + telRefDos + '\'' +
                '}';
    }

}
