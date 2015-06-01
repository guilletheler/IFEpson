/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

/**
 * Tipo de salida
 */
public enum TipoSalida {

    /**
     * Formulario contínuo
     */
    FORMULARIO_CONTINUO("C"),
    /**
     * Hoja Suelta
     */
    HOJA_SUELTA("S");

    String letra = "C";

    TipoSalida(String letra) {
        this.letra = letra;
    }

    /**
     * Abreviatura
     *
     * @return
     */
    public String getLetra() {
        return letra;
    }

    /**
     * Convierte la abreviatura en TipoSalida
     *
     * @param letra
     * @return
     */
    public static TipoSalida parseLetra(String letra) {
        switch (letra.charAt(0)) {
            case 'C':
                return TipoSalida.FORMULARIO_CONTINUO;
            case 'S':
                return TipoSalida.HOJA_SUELTA;

        }
        return null;
    }
}
