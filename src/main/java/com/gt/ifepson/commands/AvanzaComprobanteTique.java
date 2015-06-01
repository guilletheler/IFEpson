/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gt.ifepson.commands;

/**
 *
 * @author guillermot
 */
public class AvanzaComprobanteTique extends AvanzaTique {

    /**
     * Constructor
     */
    public AvanzaComprobanteTique() {
        super();
        this.nombreAbreviado = "AVANZACOMPROBANTETIQUE";
        this.descripcionComando = "Avanza solo el papel del comprobante del tique, spara los impresores que lo soporten";
        this.setTipoAvance(TipoAvance.PAPEL_COMPROBANTE);
    }


    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }
}
