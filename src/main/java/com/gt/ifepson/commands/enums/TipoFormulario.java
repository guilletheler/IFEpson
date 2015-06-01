/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

/**
 * Tipo de formulario
 */
public enum TipoFormulario {

    /**
     * Pre-impreso
     */
    PRE_IMPRESO("F"),
    /**
     * Papel blanco
     */
    BLANCO("P"),
    /**
     * Autoimpresor
     */
    AUTOIMPRESOR("A");

    String letra = "";

    TipoFormulario(String letra) {
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
     * Convierte la abreviatura en TipoFormulario
     *
     * @param letra
     * @return
     */
    public static TipoFormulario parseLetra(String letra) {

        switch (letra.charAt(0)) {
            case 'F':
                return TipoFormulario.PRE_IMPRESO;
            case 'P':
                return TipoFormulario.BLANCO;
            case 'A':
                return TipoFormulario.AUTOIMPRESOR;
        }
        return null;
    }
}
