package com.arleckk.edcobranza.model;

/**
 * Created by arleckk on 7/13/16.
 */
public class TrabajadorFonacot {

    private String credito;
    private String usuario;
    private String asignacion;
    private String fechaAsignacion;
    private String numTrabajador;
    private String nombreTrabajador;
    private String telTrabajador;
    private String tipo;
    private String calle;
    private String delegacion;
    private String estado;
    private String codigoPostal;
    private String montoInicial;
    private String saldoActual;
    private String fechaEjercidaCredito;
    private String sucursalEjercimiento;
    private String pagoMensual;
    private String plazo;
    private String telefonoTrabajo;
    private String rfc;
    private String nss;
    private String email;
    private String telExtraUno;
    private String telExtraDos;
    private String direccion;

    public TrabajadorFonacot(String credito, String usuario, String asignacion, String fechaAsignacion,
                             String numTrabajador, String nombreTrabajador, String telTrabajador, String tipo,
                             String calle, String delegacion, String estado, String codigoPostal, String montoInicial,
                             String saldoActual, String fechaEjercidaCredito, String sucursalEjercimiento, String pagoMensual,
                             String plazo, String telefonoTrabajo, String rfc, String nss, String email, String telExtraUno,
                             String telExtraDos) {
        this.credito = credito;
        this.usuario = usuario;
        this.asignacion = asignacion;
        this.fechaAsignacion = fechaAsignacion;
        this.numTrabajador = numTrabajador;
        this.nombreTrabajador = nombreTrabajador;
        this.telTrabajador = telTrabajador;
        this.tipo = tipo;
        this.calle = calle;
        this.delegacion = delegacion;
        this.estado = estado;
        this.codigoPostal = codigoPostal;
        this.montoInicial = montoInicial;
        this.saldoActual = saldoActual;
        this.fechaEjercidaCredito = fechaEjercidaCredito;
        this.sucursalEjercimiento = sucursalEjercimiento;
        this.pagoMensual = pagoMensual;
        this.plazo = plazo;
        this.telefonoTrabajo = telefonoTrabajo;
        this.rfc = rfc;
        this.nss = nss;
        this.email = email;
        this.telExtraUno = telExtraUno;
        this.telExtraDos = telExtraDos;
        this.direccion = "";
        setDireccion();
    }

    public void setDireccion() {
        if(isValid(calle)) {
            direccion += " "+calle;
        }
        if(isValid(delegacion)) {
            direccion += " "+delegacion;
        }
        if(isValid(estado)) {
            direccion += " "+estado;
        }
        if(isValid(codigoPostal)) {
            direccion += " "+codigoPostal;
        }
    }

    public String getDireccion() {
        return direccion;
    }

    public boolean isValid(String text){
        if(text != null) {
            if(!text.equals("")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
    }

    public String getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getNumTrabajador() {
        return numTrabajador;
    }

    public void setNumTrabajador(String numTrabajador) {
        this.numTrabajador = numTrabajador;
    }

    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    public String getTelTrabajador() {
        return telTrabajador;
    }

    public void setTelTrabajador(String telTrabajador) {
        this.telTrabajador = telTrabajador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(String montoInicial) {
        this.montoInicial = montoInicial;
    }

    public String getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(String saldoActual) {
        this.saldoActual = saldoActual;
    }

    public String getFechaEjercidaCredito() {
        return fechaEjercidaCredito;
    }

    public void setFechaEjercidaCredito(String fechaEjercidaCredito) {
        this.fechaEjercidaCredito = fechaEjercidaCredito;
    }

    public String getSucursalEjercimiento() {
        return sucursalEjercimiento;
    }

    public void setSucursalEjercimiento(String sucursalEjercimiento) {
        this.sucursalEjercimiento = sucursalEjercimiento;
    }

    public String getPagoMensual() {
        return pagoMensual;
    }

    public void setPagoMensual(String pagoMensual) {
        this.pagoMensual = pagoMensual;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getTelefonoTrabajo() {
        return telefonoTrabajo;
    }

    public void setTelefonoTrabajo(String telefonoTrabajo) {
        this.telefonoTrabajo = telefonoTrabajo;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelExtraUno() {
        return telExtraUno;
    }

    public void setTelExtraUno(String telExtraUno) {
        this.telExtraUno = telExtraUno;
    }

    public String getTelExtraDos() {
        return telExtraDos;
    }

    public void setTelExtraDos(String telExtraDos) {
        this.telExtraDos = telExtraDos;
    }

    @Override
    public String toString() {
        return "TrabajadorFonacot{" +
                "credito='" + credito + '\'' +
                ", usuario='" + usuario + '\'' +
                ", asignacion='" + asignacion + '\'' +
                ", fechaAsignacion='" + fechaAsignacion + '\'' +
                ", numTrabajador='" + numTrabajador + '\'' +
                ", nombreTrabajador='" + nombreTrabajador + '\'' +
                ", telTrabajador='" + telTrabajador + '\'' +
                ", tipo='" + tipo + '\'' +
                ", calle='" + calle + '\'' +
                ", delegacion='" + delegacion + '\'' +
                ", estado='" + estado + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", montoInicial='" + montoInicial + '\'' +
                ", saldoActual='" + saldoActual + '\'' +
                ", fechaEjercidaCredito='" + fechaEjercidaCredito + '\'' +
                ", sucursalEjercimiento='" + sucursalEjercimiento + '\'' +
                ", pagoMensual='" + pagoMensual + '\'' +
                ", plazo='" + plazo + '\'' +
                ", telefonoTrabajo='" + telefonoTrabajo + '\'' +
                ", rfc='" + rfc + '\'' +
                ", nss='" + nss + '\'' +
                ", email='" + email + '\'' +
                ", telExtraUno='" + telExtraUno + '\'' +
                ", telExtraDos='" + telExtraDos + '\'' +
                '}';
    }
}
