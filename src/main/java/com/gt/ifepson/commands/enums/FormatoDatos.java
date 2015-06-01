/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

/**
 * Formato de datos
 */
public enum FormatoDatos {

    /**
     * no documento no fiscal homologado
     */
    NO_DNFH("C"),
    /**
     * si documento no fiscal homologado
     */
    SI_DNFH("G");

    FormatoDatos(String letra) {
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
     * Convierte abreviatura en FormatoDatos
     *
     * @param letra
     * @return
     */
    public static FormatoDatos parseLetra(String letra) {
        switch (letra.charAt(0)) {
            case 'C':
                return FormatoDatos.NO_DNFH;
            case 'G':
                return FormatoDatos.SI_DNFH;
        }
        return null;
    }
}
