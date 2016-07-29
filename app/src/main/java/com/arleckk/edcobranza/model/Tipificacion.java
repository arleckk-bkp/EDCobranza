package com.arleckk.edcobranza.model;

/**
 * Created by arleckk on 7/21/16.
 */
public class Tipificacion {

    private String id;
    private String creditoTrabajador;
    private String usuario;
    private String estatus;
    private String subestatus;
    private String fechaGestion;
    private String comentario;
    private String montoPago;
    private String fechaPago;
    private String porcQuita;
    private String fechaDev;
    private String lugarDev;
    private String montoRees;
    private String plazoRees;
    private String fechaVisita;
    private String descOfrecido;

    public Tipificacion(String id, String creditoTrabajador, String usuario, String estatus, String subestatus, String fechaGestion,
                        String comentario, String montoPago, String fechaPago, String porcQuita, String fechaDev, String lugarDev,
                        String montoRees, String plazoRees, String fechaVisita, String descOfrecido) {
        this.id = id;
        this.creditoTrabajador = creditoTrabajador;
        this.usuario = usuario;
        this.estatus = estatus;
        this.subestatus = subestatus;
        this.fechaGestion = fechaGestion;
        this.comentario = comentario;
        this.montoPago = montoPago;
        this.fechaPago = fechaPago;
        this.porcQuita = porcQuita;
        this.fechaDev = fechaDev;
        this.lugarDev = lugarDev;
        this.montoRees = montoRees;
        this.plazoRees = plazoRees;
        this.fechaVisita = fechaVisita;
        this.descOfrecido = descOfrecido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreditoTrabajador() {
        return creditoTrabajador;
    }

    public void setCreditoTrabajador(String creditoTrabajador) {
        this.creditoTrabajador = creditoTrabajador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getSubestatus() {
        return subestatus;
    }

    public void setSubestatus(String subestatus) {
        this.subestatus = subestatus;
    }

    public String getFechaGestion() {
        return fechaGestion;
    }

    public void setFechaGestion(String fechaGestion) {
        this.fechaGestion = fechaGestion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(String montoPago) {
        this.montoPago = montoPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getPorcQuita() {
        return porcQuita;
    }

    public void setPorcQuita(String porcQuita) {
        this.porcQuita = porcQuita;
    }

    public String getFechaDev() {
        return fechaDev;
    }

    public void setFechaDev(String fechaDev) {
        this.fechaDev = fechaDev;
    }

    public String getLugarDev() {
        return lugarDev;
    }

    public void setLugarDev(String lugarDev) {
        this.lugarDev = lugarDev;
    }

    public String getMontoRees() {
        return montoRees;
    }

    public void setMontoRees(String montoRees) {
        this.montoRees = montoRees;
    }

    public String getPlazoRees() {
        return plazoRees;
    }

    public void setPlazoRees(String plazoRees) {
        this.plazoRees = plazoRees;
    }

    public String getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(String fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public String getDescOfrecido() {
        return descOfrecido;
    }

    public void setDescOfrecido(String descOfrecido) {
        this.descOfrecido = descOfrecido;
    }
}
