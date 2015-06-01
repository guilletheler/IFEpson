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
public class SubtotalTique extends IfCommand {

    /**
     * imprime
     * @return 
     */
    public boolean isImprimir() {
        return this.params.get(InParam.STT__IMPRIMIR).equals("P");
    }

    /**
     * imprime
     * @param imprimir 
     */
    public void setImprimir(boolean imprimir) {
        this.params.put(InParam.STT__IMPRIMIR, imprimir ? "P" : "N");
    }

    /**
     * constructor
     */
    public SubtotalTique() {
        this.commandId = 0x43;
        this.name = "subtotalTiqueFiscal";
        this.nombreAbreviado = "TIQUESUBTOTAL";
        this.descripcionComando = "Este comando será rechazado si no hay un comprobante Tique fiscal abierto. Se rechazará si la acumulación de montos genera un desborde de totales. Se usa este comando para enviar los totales de transacciones al Host e imprimir, opcionalmente, el subtotal.";

        this.params.put(InParam.STT__IMPRIMIR, "P");
        this.params.put(InParam.STT__DESCRIPCION, "                         ");
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            respuesta.put(OutParam.STT__CANT_ITEMS, IfCommand.extractVarStrFromArray(resp, 16, (char) 0x1C));
            respuesta.put(OutParam.STT__TOTAL_A_PAGAR_BRUTO, IfCommand.extractVarStrFromArray(resp, 22, (char) 0x1C));
            respuesta.put(OutParam.STT__TOTAL_IVA, IfCommand.extractVarStrFromArray(resp, 35, (char) 0x1C));
            respuesta.put(OutParam.STT__TOTAL_PAGO, IfCommand.extractVarStrFromArray(resp, 48, (char) 0x1C));
            respuesta.put(OutParam.STT__TOTAL_IMP_INT_PORCENT, IfCommand.extractVarStrFromArray(resp, 61, (char) 0x1C));
            respuesta.put(OutParam.STT__TOTAL_IMP_INT_FIJO, IfCommand.extractVarStrFromArray(resp, 74, (char) 0x1C));
            respuesta.put(OutParam.STT__MONTO_NETO, IfCommand.extractVarStrFromArray(resp, 87, (char) 0x1C, (char) 0x03));
        } catch (Exception ex) {
            Logger.getLogger(SubtotalTique.class).log(Level.ERROR, "", ex);
            return false;
        }

        return true;
    }
}
