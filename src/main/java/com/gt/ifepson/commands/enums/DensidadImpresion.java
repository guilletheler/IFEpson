/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

/**
 * Densidad de la impresion, en caracteres por pulgada
 */
public enum DensidadImpresion {

    /**
     * 12 cpi
     */
    CPI12("12"),
    /**
     * 17 cpi
     */
    CPI17("17");

    String cpi = "12";

    DensidadImpresion(String cpi) {
        this.cpi = cpi;
    }

    /**
     * valor de los cpi
     *
     * @return
     */
    public String getCpi() {
        return cpi;
    }

    /**
     * Convierte el valor de la densidad en DensidadImpresion
     *
     * @param letra
     * @return
     */
    public static DensidadImpresion parseLetra(String letra) {
        if (letra.equalsIgnoreCase("12")) {
            return DensidadImpresion.CPI12;
        }

        return DensidadImpresion.CPI17;
    }
}
