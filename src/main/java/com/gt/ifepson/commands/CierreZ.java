/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gt.ifepson.commands;

import com.gt.ifepson.commands.enums.TipoCierre;

/**
 *
 * @author guillermot
 */
public class CierreZ extends CierreXZ {

    /**
     * Constructor
     */
    public CierreZ() {
        super();
        this.descripcionComando = "Realiza un cierre Z, no es necesario enviar ningun parametro";
        this.nombreAbreviado = "CIERREZ";
        this.setTipoCierre(TipoCierre.Z);
        setImprimir(true);
    }


}
