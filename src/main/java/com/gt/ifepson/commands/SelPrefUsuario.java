/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.commands.enums.TipoPreferenciasUsuario;
import com.gt.ifepson.commands.enums.Opcion1PreferenciasUsuario;
import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;

/**
 * Si en el Campo 02 se envío ‘D’: ‘S’ 0x53 ASCII <br/>
 * (83 Decimal)para indicar que se utilizará como estación seleccionada por el
 * usuario el slip paper (hoja suelta). <br/>
 * ‘R’ 0x52 ASCII (82 Decimal)para indicar que se utilizará como estación
 * seleccionada por el usuario la de rollo de papel. <br/>
 * Si en el Campo 02 se envío ‘P’: ‘S’ 0x53 ASCII(83 Decimal)indica que se
 * establecerá el tamaño de papel. <br/>
 * Si en el Campo 02 se envío ‘T’: ‘P’ 0x50 ASCII (80 Decimal) se envía para
 * indicar que se establecerán las <br/>
 * preferencias de imprimir las leyendas “Suma de sus pagos” y “Su Vuelto”.
 * <br/>
 * ‘Q’ 0x51 ASCII (81 Decimal) se envía para indicar que se establecerán las
 * preferencias de imprimir Precio por Cantidad en cada ítem facturado.
 *
 * @author guillermot
 */
public class SelPrefUsuario extends IfCommand {

    /**
     * Cantidad de columnas
     *
     * @return
     */
    public int getCantColumnas() {
        return Integer.parseInt(params.get(InParam.SPU__CANT_COLUMNAS));
    }

    /**
     * cantidad de columnas
     *
     * @param cantColumnas
     */
    public void setCantColumnas(int cantColumnas) {
        this.params.put(InParam.SPU__CANT_COLUMNAS, ((Integer) cantColumnas).toString());
    }

    /**
     * Cantidad de filas
     * @return 
     */
    public int getCantFilas() {
        return Integer.parseInt(params.get(InParam.SPU__CANT_FILAS));
    }

    /**
     * Cantidad de filas
     * @param cantFilas 
     */
    public void setCantFilas(int cantFilas) {
        this.params.put(InParam.SPU__CANT_FILAS, ((Integer) cantFilas).toString());
    }

    /**
     * Opcion 1
     * @return 
     */
    public Opcion1PreferenciasUsuario getOpcion1() {
        return Opcion1PreferenciasUsuario.parseString(params.get(InParam.SPU__OPCIONES1));
    }

    /**
     * Opcion 1
     * @param opcion1 
     */
    public void setOpcion1(Opcion1PreferenciasUsuario opcion1) {
        this.params.put(InParam.SPU__OPCIONES1, opcion1.getLetra());
    }

    /**
     * Opcion 2
     * @return 
     */
    public Opcion2PreferenciasUsuario getOpcion2() {
        return Opcion2PreferenciasUsuario.parseLetra(params.get(InParam.SPU__OPCIONES2));
    }

    /**
     * Opción 2
     * @param opcion2 
     */
    public void setOpcion2(Opcion2PreferenciasUsuario opcion2) {
        this.params.put(InParam.SPU__OPCIONES2, opcion2.getLetra());
    }

    /**
     * Tipo de seteo
     * @return 
     */
    public TipoPreferenciasUsuario getTipoSeteo() {
        return TipoPreferenciasUsuario.parseString(params.get(InParam.SPU__TIPO_SETEO));
    }

    /**
     * tipo de seteo
     * @param tipoSeteo 
     */
    public void setTipoSeteo(TipoPreferenciasUsuario tipoSeteo) {
        this.params.put(InParam.SPU__TIPO_SETEO, tipoSeteo.getLetra());
    }

    /**
     * Constructor
     */
    public SelPrefUsuario() {
        this.commandId = 0x5A;
        this.name = "seleccionarPreferenciasUsuario";
        this.descripcionComando = "Este comando permite realizar configuraciones sobre la impresión en Hoja Suelta y/o Formulario Continuo, así como también seleccionar determinadas preferencias en comprobantes. Para saber cual es la configuración actual, se puede utilizar el comando Leer Preferencias del Usuario.";

        this.params.put(InParam.SPU__IMPRESORA, "P");
        this.params.put(InParam.SPU__TIPO_SETEO, TipoPreferenciasUsuario.PREFERENCIAS_DISPOSITIVO_IMPRESION.getLetra());
        this.params.put(InParam.SPU__OPCIONES1, Opcion1PreferenciasUsuario.PREF_IMPRIMIR_LEYENDA.getLetra());
        this.params.put(InParam.SPU__OPCIONES2, Opcion2PreferenciasUsuario.SELECCIONAR.getLetra());
        this.params.put(InParam.SPU__CANT_COLUMNAS, "0");
        this.params.put(InParam.SPU__CANT_FILAS, "0");
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

    /**
     * Opcion 2
     */
    public enum Opcion2PreferenciasUsuario {

        /**
         * Se imprimen documentos no fiscales
         */
        SE_IMPRIMEN_DOCUMENTOS_NO_FISC("O"),
        /**
         * tamaño definido por usuario
         */
        TAMA_DEFINIDO_POR_USUARIO("U"),
        /**
         * deseleccionar
         */
        DESELECCIONAR("N"),
        /**
         * seleccionar
         */
        SELECCIONAR("S");
        
        String letra = "";

        Opcion2PreferenciasUsuario(String letra) {
            this.letra = letra;
        }

        /**
         * abreviatura
         * @return 
         */
        public String getLetra() {
            return letra;
        }

        /**
         * convierte abreviatura en Opcion2PreferenciasUsuario
         * @param letra
         * @return 
         */
        public static Opcion2PreferenciasUsuario parseLetra(String letra) {
            switch (letra.charAt(0)) {
                case 'O':
                    return Opcion2PreferenciasUsuario.SE_IMPRIMEN_DOCUMENTOS_NO_FISC;
                case 'U':
                    return Opcion2PreferenciasUsuario.TAMA_DEFINIDO_POR_USUARIO;
                case 'N':
                    return Opcion2PreferenciasUsuario.DESELECCIONAR;
                case 'S':
                    return Opcion2PreferenciasUsuario.SELECCIONAR;
            }
            return null;
        }
    }
}
