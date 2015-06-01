/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gt.ifepson.commands;

/**
 *
 * @author guillermot
 */
public class AvanzaCintaTestigoTique extends AvanzaTique {

    /**
     * Constructor
     */
    public AvanzaCintaTestigoTique() {
        super();
        this.nombreAbreviado = "AVANZATESTIGOTIQUE";
        this.descripcionComando = "Avanza solo la cinta testigo del tique, spara los impresores que lo soporten";
        this.setTipoAvance(TipoAvance.PAPEL_CINTA_TESTIGO);
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }


}
