/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.IfCommand;

/**
 * Comando Abrir Documento No Fiscal
 * @author guillermot
 */
public class AbrirDNF extends IfCommand {

    /**
     * Crea un nuevo comando Abrir Documento No Fiscal
     */
    public AbrirDNF() {
        this.commandId = 0x48;
        this.name = "abrirDocumentoNoFiscal";
        this.nombreAbreviado = "ABRIRDNF";
        this.descripcionComando = "Este comando es el primer paso en la producción de un Documento No Fiscal. Se imprime el encabezado del comprobante fiscal, pero se lo identifica claramente como \"NO FISCAL\".";

    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }
}
