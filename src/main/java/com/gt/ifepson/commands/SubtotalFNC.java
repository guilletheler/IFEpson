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
public class SubtotalFNC extends IfCommand {

    /**
     * imprime
     * @return 
     */
    public boolean isImprimir() {
        return this.params.get(InParam.STFNC__IMPRIMIR).equals("P");
    }

    /**
     * imprime
     * @param imprimir 
     */
    public void setImprimir(boolean imprimir) {
        this.params.put(InParam.STFNC__IMPRIMIR, imprimir ? "P" : "N");
    }

    /**
     * Constructor
     */
    public SubtotalFNC() {
        this.commandId = 0x63;
        this.name = "subtotalTiqueFNC";
        this.nombreAbreviado = "FACTSUBTOTAL";
        this.descripcionComando = "Este comando será rechazado si no hay un comprobante fiscal abierto. Se usa este comando para enviar los totales de transacciones al Host.";

        this.params.put(InParam.STFNC__IMPRIMIR, "P");
        this.params.put(InParam.STFNC__DESCRIPCION, " ");
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            respuesta.put(OutParam.STFNC__CANT_ITEMS, IfCommand.extractVarStrFromArray(resp, 16, (char) 0x1C));
            respuesta.put(OutParam.STFNC__TOTAL_A_PAGAR_BRUTO, IfCommand.extractVarStrFromArray(resp, 22, (char) 0x1C));
            respuesta.put(OutParam.STFNC__TOTAL_IVA, IfCommand.extractVarStrFromArray(resp, 35, (char) 0x1C));
            respuesta.put(OutParam.STFNC__TOTAL_PAGO, IfCommand.extractVarStrFromArray(resp, 48, (char) 0x1C));
            respuesta.put(OutParam.STFNC__TOTAL_IMP_INT_PORCENT, IfCommand.extractVarStrFromArray(resp, 61, (char) 0x1C));
            respuesta.put(OutParam.STFNC__TOTAL_IMP_INT_FIJO, IfCommand.extractConstStrFromArray(resp, 74, 12));
            respuesta.put(OutParam.STFNC__MONTO_NETO, IfCommand.extractConstStrFromArray(resp, 87, 12));
        } catch (Exception ex) {
            Logger.getLogger(SubtotalFNC.class).log(Level.ERROR, "", ex);
            return false;
        }

        return true;
    }
}
