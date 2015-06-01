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
public class ImprTxtDNF extends IfCommand {

    /**
     * texto
     * @return 
     */
    public String getTexto() {
        return params.get(InParam.ITDNF__DESCRIPCION);
    }

    /**
     * texto
     * @param texto 
     */
    public void setTexto(String texto) {
        if (texto.length() > 40) {
            texto = texto.substring(0, 39);
        }
        params.put(InParam.ITDNF__DESCRIPCION, texto);
    }

    /**
     * constructor
     */
    public ImprTxtDNF() {
        this.commandId = 0x49;
        this.name = "imprimirTextoEnDocumNoFiscal";
        this.nombreAbreviado = "IMPRTXTDNF";
        this.descripcionComando = "El comando será rechazado si no está abierto un comprobante no fiscal. Se restringirá el texto al conjunto de Caracteres del Texto Fiscal.";

        this.params.put(InParam.ITDNF__DESCRIPCION, String.format("%0$-40s", ""));
    }


    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }
}
