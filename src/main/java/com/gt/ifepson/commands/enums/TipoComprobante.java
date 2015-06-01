/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

/**
 * Tipo de salida
 */
public enum TipoComprobante {

    /**
     * Factura (a o b)
     */
    FACTURA("F"),
    /**
     * Nota de crédito
     */
    NOTA_CREDITO("N"),
    /**
     * Nota de débito OJO!!!!! NO ESTÁ PROBADO
     */
    NOTA_DEBITO("D"),
    /**
     * Tiquet factura
     */
    TIQUE_FACTURA("T"),
    /**
     * Tiquet nota de crédito
     */
    TIQUE_NOTA_CREDITO("M");

    TipoComprobante(String letra) {
        this.letra = letra;
    }

    String letra = "F";

    /**
     * abreviatura
     *
     * @return
     */
    public String getLetra() {
        return letra;
    }

    /**
     * Convierte la abreviatura en TipoComprobante
     *
     * @param letra
     * @return
     */
    public static TipoComprobante parseLetra(String letra) {

        switch (letra.charAt(0)) {
            case 'F':
                return TipoComprobante.FACTURA;
            case 'N':
                return TipoComprobante.NOTA_CREDITO;
            case 'D':
                return TipoComprobante.NOTA_DEBITO;
            case 'T':
                return TipoComprobante.TIQUE_FACTURA;
            case 'M':
                return TipoComprobante.TIQUE_NOTA_CREDITO;
        }

        return null;
    }
}
