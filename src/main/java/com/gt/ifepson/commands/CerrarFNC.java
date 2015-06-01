/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;
import com.gt.ifepson.capa_fisica.OutParam;
import com.gt.ifepson.commands.enums.LetraDocumento;
import com.gt.ifepson.commands.enums.TipoComprobante;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public class CerrarFNC extends IfCommand {

    /**
     * descripcion del total
     * @return 
     */
    public String getDescripcionTotal() {
        return params.get(InParam.CFNC__DESCRIP_EN_TOTAL);
    }

    /**
     * descripcion del total
     * @param descripcionTotal 
     */
    public void setDescripcionTotal(String descripcionTotal) {
       params.put(InParam.CFNC__DESCRIP_EN_TOTAL, descripcionTotal);
    }

    /**
     * letra del documento
     * @return 
     */
    public LetraDocumento getLetra() {
        return LetraDocumento.parseLetra(params.get(InParam.CFNC__LETRA_DOCUMENTO));
    }

    /**
     * letra del documento
     * @param letra 
     */
    public void setLetra(LetraDocumento letra) {
        params.put(InParam.CFNC__LETRA_DOCUMENTO, letra.getLetra());
    }

    /**
     * tipo de comprobante
     * @return 
     */
    public TipoComprobante getTipoComprobante() {
        return TipoComprobante.parseLetra(params.get(InParam.CFNC__TIPO_DOCUMENTO));
    }

    /**
     * tipo del comprobante
     * @param tipoComprobante 
     */
    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        params.put(InParam.CFNC__TIPO_DOCUMENTO, tipoComprobante.getLetra());
    }

    /**
     * constructor
     */
    public CerrarFNC() {
        this.commandId = 0x65;
        this.name = "cerrarFNC";
        this.nombreAbreviado = "FACTCIERRA";
        this.descripcionComando = "Se rechazará este comando si no hay un comprobante fiscal abierto. Se rechazará si los montos acumulados generan un desbordamiento de total.";

        this.params.put(InParam.CFNC__TIPO_DOCUMENTO, TipoComprobante.FACTURA.getLetra());
            this.params.put(InParam.CFNC__LETRA_DOCUMENTO, LetraDocumento.A.getLetra());
            this.params.put(InParam.CFNC__DESCRIP_EN_TOTAL, Character.toString((char) 0x7F));
    }


    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            respuesta.put(OutParam.CFNC__NRO_COMPROBANTE, IfCommand.extractVarStrFromArray(resp, 14, (char) 0x1C, (char) 0x03));
        } catch (Exception e) {
            Logger.getLogger(CerrarFNC.class).log(Level.ERROR, "", e);
            return false;
        }
        return true;
    }



}
