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
public class AvanzaTique extends IfCommand {

    /**
     * tipo de avance
     * @return 
     */
    public TipoAvance getTipoAvance() {
        return TipoAvance.parseLetra(commandId);
    }

    /**
     * tipo de avance
     * @param tipoAvance 
     */
    public void setTipoAvance(TipoAvance tipoAvance) {
        this.commandId = tipoAvance.getComando();
    }

    /**
     * lineas
     * @return 
     */
    public Integer getLineas() {
        return Integer.parseInt(params.get(InParam.AVT__CANTIDAD_LINEAS));
    }

    /**
     * lineas
     * @param lineas 
     */
    public void setLineas(Integer lineas) {
        this.params.put(InParam.AVT__CANTIDAD_LINEAS, String.format("%02d", lineas));
    }

    /**
     * Constructor
     */
    public AvanzaTique() {
        this.commandId = 0x4B;
        this.name = "avanzaTicket";
        this.nombreAbreviado = "AVANZATIQUE";
        this.descripcionComando = "Avanza el papel del ticket";

        this.commandId = TipoAvance.AMBOS.getComando();
        this.params.put(InParam.AVT__CANTIDAD_LINEAS, String.format("%02d", 1));
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

    /**
     * Tipo de avance del papel
     */
    public enum TipoAvance {

        /**
         * Papel del comprobante
         */
        PAPEL_COMPROBANTE((byte) 0x50),
        /**
         * cinta testigo
         */
        PAPEL_CINTA_TESTIGO((byte) 0X51),
        /**
         * ambas
         */
        AMBOS((byte) 0X52);
        byte comando = 0x52;

        TipoAvance(byte comando) {
            this.comando = comando;
        }

        /**
         * comando binario
         * @return 
         */
        public byte getComando() {
            return comando;
        }

        /**
         * convierte el tipo de comando en TipoAvance
         * @param comando
         * @return 
         */
        public static TipoAvance parseLetra(byte comando) {

            switch (comando) {
                case (byte) 0x50:
                    return TipoAvance.PAPEL_COMPROBANTE;
                case (byte) 0x51:
                    return TipoAvance.PAPEL_CINTA_TESTIGO;
                case (byte) 0x52:
                    return TipoAvance.AMBOS;
            }

            return null;
        }
    }
}
