/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gt.ifepson.commands;

import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;

/**
 *
 * @author guillermot
 */
public class SetDatoFijo extends IfCommand {

    /**
     * dato fijo
     * @return 
     */
    public String getDatoFijo() {
        return params.get(InParam.EDF__DATOS_FIJOS);
    }

    /**
     * dato fijo
     * @param datoFijo 
     */
    public SetDatoFijo(String datoFijo) {
        this.params.put(InParam.EDF__DATOS_FIJOS, datoFijo);
    }

    /**
     * nro de linea
     * @return 
     */
    public int getNroLinea() {
        return Integer.parseInt(this.params.get(InParam.EDF__NRO_LINEA));
    }

    /**
     * nro de linea
     * @param nroLinea 
     */
    public void setNroLinea(int nroLinea) {
        this.params.put(InParam.EDF__NRO_LINEA, String.format("%05d",  nroLinea));
    }
    
    /**
     * constructor
     */
    public SetDatoFijo() {
        this.commandId = 0x5D;
        this.name = "setDatoFijo";
        this.nombreAbreviado = "PONEENCABEZADO";
        this.descripcionComando = "Este comando almacena una línea de Datos Fijos de encabezado o cola en la Memoria de Trabajo. Este comando permite almacenar un código de barras a ser impreso, el cual sólo es permitido en las últimas líneas de un comprobante (colas), ver Apéndice A por detalles.";

        this.params.put(InParam.EDF__NRO_LINEA, String.format("%05d",  1));
        this.params.put(InParam.EDF__DATOS_FIJOS, "");

    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

}
