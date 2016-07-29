package com.arleckk.edcobranza.global;

import com.arleckk.edcobranza.model.TrabajadorFonacot;

/**
 * Created by arleckk on 13/07/16.
 */
public class Constants {

    private static final String PORT_HOST = "80";

    private static final String IP = "http://201.161.11.66:";

    private static final String FONACOT = "/webservices/fonacot/";

    public static final String GET_FONACOT_ACCOUNTS_BY_USER = IP + PORT_HOST  + "/webservices/fonacot/obtener-creditos-usuario.php";

    public static final String GET_FONACOT_TRABAJADOR_BY_ID = IP + PORT_HOST  + "/webservices/fonacot/obtener-trabajador.php";

    public static final String GET_FONACOT_TIPIFICACION_BY_ID = IP + PORT_HOST  +"/webservices/fonacot/obtener-tipificaciones-trabajador.php";

    public static final String POST_PHOTO = IP + PORT_HOST + "/webservices/fonacot/subir-foto.php";

    public static final String POST_PHOTO_BD = IP + PORT_HOST + "/webservices/fonacot/subir-foto-bd.php";

    public static final String POST_LOCATION = IP + PORT_HOST + "/webservices/fonacot/actualizar-ubicacion.php";

    public static final String DO_LOGIN = IP + PORT_HOST + "/webservices/fonacot/do-login.php";

    public static final String GET_ESTATUS = IP + PORT_HOST + "/webservices/fonacot/get-estatus.php";

    public static final String GET_SUBESTATUS = IP + PORT_HOST + "/webservices/fonacot/get-subestatus.php";

    public static final String POST_CAPTURA_PROMESA = IP + PORT_HOST + "/webservices/fonacot/capturar-promesa.php";

    public static final String POST_CAPTURA_CONVENIO = IP + PORT_HOST + "/webservices/fonacot/capturar-convenio.php";

    public static final String POST_CAPTURA_DEVOLUCION = IP + PORT_HOST + "/webservices/fonacot/capturar-devolucion.php";

    public static final String POST_CAPTURA_REESTRUCTURA = IP + PORT_HOST + "/webservices/fonacot/capturar-reestructura.php";

    public static final String POST_CAPTURA_VISITA = IP + PORT_HOST + "/webservices/fonacot/capturar-visita.php";

    public static final String POST_CAPTURA_PAGADA = IP + PORT_HOST + "/webservices/fonacot/capturar-pagada.php";

    public static final String POST_CAPTURA_BONIFICAR = IP + PORT_HOST + "/webservices/fonacot/capturar-bonificar.php";

    public static final String POST_CAPTURA_GENERAL = IP + PORT_HOST + "/webservices/fonacot/capturar-general.php";

    public static final String UPDATE_LOCATION = IP + PORT_HOST + "/webservices/fonacot/actualizar-ubicacion.php";

}
