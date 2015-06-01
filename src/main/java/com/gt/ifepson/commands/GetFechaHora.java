/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gt.ifepson.commands;

import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.OutParam;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public class GetFechaHora extends IfCommand {

    /**
     * Constructor
     */
    public GetFechaHora() {
        this.commandId = 0x59;
        this.name = "getFechaHora";
        this.descripcionComando = "Obtener fecha y hora";
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            respuesta.put(OutParam.OFH__FECHA, IfCommand.extractConstStrFromArray(resp, 14, 6));
            respuesta.put(OutParam.OFH__HORA, IfCommand.extractConstStrFromArray(resp, 21, 6));
        } catch (Exception e) {
            Logger.getLogger(GetFechaHora.class).log(Level.ERROR, "", e);
            return false;
        }
        return true;
    }

}
