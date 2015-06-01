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
public class CierreX extends CierreXZ {

    /**
     * Constructor
     */
    public CierreX() {
        super();
        this.descripcionComando = "Realiza un cierre X, no es necesario enviar ningun parametro";
        this.nombreAbreviado = "CIERREX";
        this.setTipoCierre(TipoCierre.X);
        setImprimir(true);
    }



}
