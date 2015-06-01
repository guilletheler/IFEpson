/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;
import com.gt.ifepson.capa_fisica.OutParam;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public class CerrarDNF extends IfCommand {

    /**
     * corte del total
     * @return 
     */
    public boolean isCorteTotal() {
        return this.params.get(InParam.PDRC__DESCRIPCION).equals("T");
    }

    /**
     * corte del total
     * @param corteTotal 
     */
    public void setCorteTotal(boolean corteTotal) {
        if (corteTotal) {
            this.params.put(InParam.PDRC__DESCRIPCION, "T");
        } else {
            this.params.put(InParam.PDRC__DESCRIPCION, "P");
        }
    }

    /**
     * constructor
     */
    public CerrarDNF() {
        this.commandId = 0x4a;
        this.name = "cerrarDNF";
        this.descripcionComando = " l comando será rechazado si un comprobante no fiscal no está abierto. Se lo rechazará si hay formularios en las entradas para impresión o validación de hojas sueltas.";
        this.nombreAbreviado = "CERRARDNF";
        this.params.put(InParam.PDRC__DESCRIPCION, "T");
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            respuesta.put(OutParam.CDNF__NRO_TIQUE, IfCommand.extractVarStrFromArray(resp, 14, (char) 0x1C, (char) 0x03));
        } catch (Exception e) {
            Logger.getLogger(CerrarDNF.class).log(Level.ERROR, "", e);
            return false;
        }
        return true;
    }
}
