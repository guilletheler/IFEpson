/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * CalificadorPagoCancelDescReca
 */
public enum CalificadorPagoCancelDescReca {

    /**
     * Cancelar comprobante
     */
    CANCELAR_COMPROBANTE("C"),
    /**
     * suma de importe pagado
     */
    SUMA_IMPORTE_PAGADO("T"),
    /**
     * anula importe pagado
     */
    ANULA_IMPORTE_PAGADO("t"),
    /**
     * descuento
     */
    DESCUENTO("D"),
    /**
     * recargo
     */
    RECARGO("R");

    CalificadorPagoCancelDescReca(String letra) {
        this.letra = letra;
    }

    String letra = "";

    /**
     * abreviatura
     *
     * @return
     */
    public String getLetra() {
        return letra;
    }

    /**
     * Abreviatura a CalificadorPagoCancelDescReca
     *
     * @param letra
     * @return
     */
    public static CalificadorPagoCancelDescReca parseString(String letra) {

        switch (letra.charAt(0)) {
            case 'C':
                return CalificadorPagoCancelDescReca.CANCELAR_COMPROBANTE;
            case 'T':
                return CalificadorPagoCancelDescReca.SUMA_IMPORTE_PAGADO;
            case 't':
                return CalificadorPagoCancelDescReca.ANULA_IMPORTE_PAGADO;
            case 'D':
                return CalificadorPagoCancelDescReca.DESCUENTO;
            case 'R':
                return CalificadorPagoCancelDescReca.RECARGO;
        }

        Logger.getLogger(CalificadorPagoCancelDescReca.class).log(Level.ERROR, "\"no se reconoce \" + letra + \"como calificador en pago de factura\"");

        return null;
    }
}
