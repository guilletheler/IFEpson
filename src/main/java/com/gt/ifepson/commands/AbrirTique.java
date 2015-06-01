/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.commands.enums.FormatoDatos;
import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;

/**
 *
 * @author guillermot
 */
public class AbrirTique extends IfCommand {


    /**
     * Formato de datos
     * @return 
     */
    public FormatoDatos getFormatoDatos() {
        return FormatoDatos.parseLetra(params.get(InParam.ABT__FORMATO_DATOS));
    }

    /**
     * Formato de datos
     * @param formatoDatos 
     */
    public void setFormatoDatos(FormatoDatos formatoDatos) {
        params.put(InParam.ABT__FORMATO_DATOS, formatoDatos.getLetra());
    }

    /**
     * Constructor
     */
    public AbrirTique() {
        this.commandId = 0x40;
        this.name = "AbrirComprobanteTiqueFiscal";
        this.nombreAbreviado = "TIQUEABRE";
        this.descripcionComando = "Este comando es el primer paso para producir un comprobante fiscal Tique. Los datos de la Hora y Fecha son impresos en el momento en que se facture el primer ítem o que se envíe una línea de texto Fiscal. Se rechazará el comando si hay un comprobante o Tique fiscal abierto. Se rechazará si la Memoria Fiscal está llena, si hay un error en la Memoria de Trabajo o en la Memoria Fiscal. Se rechazará si hay papel en las estaciones de slip o de validación.";

        this.params.put(InParam.ABT__FORMATO_DATOS, FormatoDatos.NO_DNFH.getLetra());
        
        //this.respuestaChecks.add(new RespuestaCheck(IFAction.RETRY, new Object[] {OutParam.EF__DOCUM_FISC_ABIERTO, "1"}));
    }

    
    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

}
