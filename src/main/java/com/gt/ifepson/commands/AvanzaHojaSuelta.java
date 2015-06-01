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
public class AvanzaHojaSuelta extends IfCommand {

    /**
     * lineas
     * @return 
     */
    public int getLineas() {
        return Integer.parseInt(params.get(InParam.AHS__CANTIDAD_LINEAS));
    }

    /**
     * lineas
     * @param lineas 
     */
    public void setLineas(int lineas) {
        this.params.put(InParam.AHS__CANTIDAD_LINEAS, String.format("%02d", lineas));
    }

    /**
     * Constructor
     */
    public AvanzaHojaSuelta() {
        this.commandId = 0x53;
        this.name = "avanazHojaSuelta";
        this.descripcionComando = "Avanza el papel de la hoja suelta";

        this.params.put(InParam.AHS__CANTIDAD_LINEAS, String.format("%02d", 0));
    }

  

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }
}
