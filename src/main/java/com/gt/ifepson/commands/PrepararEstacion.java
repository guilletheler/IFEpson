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
public class PrepararEstacion extends IfCommand {

    /**
     * Constructor
     */
    public PrepararEstacion() {
        this.commandId = 0x5C;
        this.name = "prepararEstacionPrincipal";
        this.descripcionComando = "Este comando se utiliza para preparar la estación indicada en el comando, para la impresión del próximo documento.";

        this.params.put(InParam.PEP__MANEJO_DOCUMENTO, "D");
        this.params.put(InParam.PEP__IMPRESION, "P");
        this.params.put(InParam.PEP__IMPRESION1, "P");
        this.params.put(InParam.PEP__IMP_DNF_HOJA_SUEL, "U");
        this.params.put(InParam.PEP__IMP_DNF, "O");
    }


    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }
}
