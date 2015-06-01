/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

/**
 * Opciones 1 de preferencias de usuario
 * @author guillermot
 */
public enum Opcion1PreferenciasUsuario {

    /**
     * Tamaño del papel
     */
    TAMA_PAPEL("S"),
    /**
     * Imprimir leyenda
     */
    PREF_IMPRIMIR_LEYENDA("P"),
    /**
     * Imprimir precio x cantidad
     */
    PREF_IMPRIMIR_PREC_X_CANTIDAD("Q");
    String letra = "";

    Opcion1PreferenciasUsuario(String letra) {
        this.letra = letra;
    }

    /**
     * Abreviatura
     * @return 
     */
    public String getLetra() {
        return letra;
    }

    /**
     * Convierte la aobreviatura en Opcion1PreferenciasUsuario
     * @param letra
     * @return 
     */
    public static Opcion1PreferenciasUsuario parseString(String letra) {
        switch (letra.charAt(0)) {
            case 'S':
                return Opcion1PreferenciasUsuario.TAMA_PAPEL;
            case 'P':
                return Opcion1PreferenciasUsuario.PREF_IMPRIMIR_LEYENDA;
            case 'Q':
                return Opcion1PreferenciasUsuario.PREF_IMPRIMIR_PREC_X_CANTIDAD;
        }

        return null;
    }
}
