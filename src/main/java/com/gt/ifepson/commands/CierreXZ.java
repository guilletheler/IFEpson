/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.commands.enums.TipoCierre;
import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;
import com.gt.ifepson.capa_fisica.OutParam;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrador
 */
public class CierreXZ extends IfCommand {

    /**
     * tipo de cierre
     *
     * @return
     */
    public TipoCierre getTipoCierre() {
        return TipoCierre.parseString(this.params.get(InParam.XZ__TIPO_CIERRE));
    }

    /**
     * tipo de cierre
     *
     * @param tipoCierre
     */
    public void setTipoCierre(TipoCierre tipoCierre) {
        this.params.put(InParam.XZ__TIPO_CIERRE, Character.toString(tipoCierre.getLetra()));
    }

    /**
     * Constructor
     */
    public CierreXZ() {
        this.commandId = 0x39;
        this.name = "CierreXZ";
        this.descripcionComando = "Este comando imprime el reporte de Totales Diarios y, en forma opcional, transfiere los Totales Diarios desde la Memoria de Trabajo a la Memoria Fiscal. Por ello, la duración de la Memoria Fiscal es independiente de la cantidad de transacciones que se realizan en el día. IMPORTANTE: Se puede realizar más de un cierre fiscal por día, pero este procedimiento acorta la vida útil de la memoria fiscal, siendo responsabilidad del programador y/o usuario la disminución en la capacidad de almacenamiento de la Memoria Fiscal.";
        
    }
    
    /**
     * indica si se imprime
     * @return 
     */
    public boolean isImprimir() {
        if (this.params.get(InParam.XZ__IMPRIMIR) == null || this.params.get(InParam.XZ__IMPRIMIR).isEmpty()) {
            this.setImprimir(true);
        }
        return this.params.get(InParam.XZ__IMPRIMIR).equals("P");
    }
    
    /**
     * indica si se imprime
     * @param imprimir 
     */
    public void setImprimir(boolean imprimir) {
        if (imprimir) {
            this.params.put(InParam.XZ__IMPRIMIR, "P");
        } else {
            this.params.put(InParam.XZ__IMPRIMIR, " ");
        }
    }
    
    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            
            String[] vals = IfCommand.bytesToStringArray(resp, (char) 0x02, (char) 0x1c, (char) 0x03);
            /*
             for (int i = 0; i < vals.length; i++) {
             Logger.getLogger(CierreXZ.class).log(Level.DEBUG, "[" + i + "] " + vals[i]);
             }
             */
            if (vals.length > 3) {
                
                respuesta.put(OutParam.XZ__NRO_CIERRE, IfCommand.extractConstStrFromArray(resp, 14, 5));
                respuesta.put(OutParam.XZ__CANT_DOC_FISC_CANCEL, IfCommand.extractConstStrFromArray(resp, 20, 5));
                respuesta.put(OutParam.XZ__CANT_DOC_NO_FISC_HOMO, IfCommand.extractConstStrFromArray(resp, 26, 5));
                respuesta.put(OutParam.XZ__CANT_DOC_NO_FISC_NO_HOMO, IfCommand.extractConstStrFromArray(resp, 32, 5));
                respuesta.put(OutParam.XZ__CANT_TIQ_FAC_B_C, IfCommand.extractConstStrFromArray(resp, 38, 5));
                respuesta.put(OutParam.XZ__CANT_TIQ_FAC_A, IfCommand.extractConstStrFromArray(resp, 44, 5));
                respuesta.put(OutParam.XZ__ULT_TIQ_FAC_B_C, IfCommand.extractConstStrFromArray(resp, 50, 8));
                respuesta.put(OutParam.XZ__TOTAL_FACT, IfCommand.extractConstStrFromArray(resp, 59, 14));
                respuesta.put(OutParam.XZ__TOTAL_IVA, IfCommand.extractConstStrFromArray(resp, 74, 14));
                respuesta.put(OutParam.XZ__TOTAL_PERCEP, IfCommand.extractConstStrFromArray(resp, 89, 14));
                respuesta.put(OutParam.XZ__ULT_TIQ_FAC_A, IfCommand.extractConstStrFromArray(resp, 104, 8));
                respuesta.put(OutParam.XZ__ULT_TIQ_NOTA_CRED_A, IfCommand.extractConstStrFromArray(resp, 113, 8));
                respuesta.put(OutParam.XZ__ULT_TIQ_NOTA_CRED_B_C, IfCommand.extractConstStrFromArray(resp, 122, 8));
                respuesta.put(OutParam.XZ__ULT_REMI, IfCommand.extractConstStrFromArray(resp, 131, 8));
                respuesta.put(OutParam.XZ__TOTAL_NOTA_CRED, IfCommand.extractConstStrFromArray(resp, 140, 14));
                respuesta.put(OutParam.XZ__TOTAL_IVA_NOTA_CRED, IfCommand.extractConstStrFromArray(resp, 155, 14));
                if (resp[169] != 0x03) {
                    respuesta.put(OutParam.XZ__TOTAL_PRECEP_NOTA_CRED, IfCommand.extractConstStrFromArray(resp, 170, 14));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CierreXZ.class).log(Level.ERROR, "", ex);
            return false;
        }
        return true;
    }
    
    
}
