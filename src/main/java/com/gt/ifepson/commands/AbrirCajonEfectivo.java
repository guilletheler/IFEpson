/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gt.ifepson.commands;

import com.gt.ifepson.IfCommand;

/**
 *
 * @author guillermot
 */
public class AbrirCajonEfectivo extends IfCommand {

    /**
     * Constructor
     */
    public AbrirCajonEfectivo() {
        this.commandId = 0x7B;
        this.name = "abrirCajonEfectivo";
        this.descripcionComando = "Abre el cajon de efectivo";
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

}
