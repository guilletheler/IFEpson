/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

/**
Tipo de preferencias de usuario
*/
public enum TipoPreferenciasUsuario {

    /**
     * Preferencias del dispositivo de impresion
     */
    PREFERENCIAS_DISPOSITIVO_IMPRESION("D"),
    /**
     * preferencias de papel
     */
    PREFERENCIAS_PAPEL("P"),
    /**
     * Preferencias de comprobantes fiscales
     */
    PREFERENCIAS_COMPROBANTES_FISCALES("T");

    String letra = "";

    TipoPreferenciasUsuario(String letra) {
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
     * Convierte abreviatura en TipoPreferenciasUsuario
     *
     * @param letra
     * @return
     */
    public static TipoPreferenciasUsuario parseString(String letra) {
        switch (letra.charAt(0)) {
            case 'D':
                return TipoPreferenciasUsuario.PREFERENCIAS_DISPOSITIVO_IMPRESION;
            case 'P':
                return TipoPreferenciasUsuario.PREFERENCIAS_PAPEL;
            case 'T':
                return TipoPreferenciasUsuario.PREFERENCIAS_COMPROBANTES_FISCALES;
        }

        return null;
    }
}
