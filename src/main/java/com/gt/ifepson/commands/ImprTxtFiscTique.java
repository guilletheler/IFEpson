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
public class ImprTxtFiscTique extends IfCommand {

    /**
     * Linea extra
     * @return 
     */
    public String getLineaExtra() {
        return params.get(InParam.ITFT__DESCRIP_EXTRA);
    }

    /**
     * Linea extra
     * @param lineaExtra 
     */
    public void setLineaExtra(String lineaExtra) {
        params.put(InParam.ITFT__DESCRIP_EXTRA, lineaExtra);
    }

    /**
     * Constructor
     */
    public ImprTxtFiscTique() {
        this.commandId = 0x41;
        this.name = "imprimirTextoFiscalEnTiqueFiscal";
        this.nombreAbreviado = "TIQUEEXTRA";
        this.descripcionComando = "Corresponde a las líneas de descripción extra de Tique Fiscal, se deben enviar en forma previa al ítem de línea respectivo, se aceptarán como máximo 4 líneas de texto fiscal consecutivas las cuales se imprimirán cuando se envíe el ítem de línea correspondiente. Se rechazará el comando si no hay un comprobante fiscal abierto. Se rechazará si hay papel en las estaciones de slip o de validación. El texto sólo puede ser Texto Fiscal. La longitud del texto está limitada de manera que no se pueda imprimir nada en las columnas que normalmente están ocupadas por campos de montos de ítems de líneas.";

        this.params.put(InParam.ITFT__DESCRIP_EXTRA, "");
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

}
