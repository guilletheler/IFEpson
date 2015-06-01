/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

/**
 * Tipo de cierre
 * @author guillermot
 */
public enum TipoCierre {

    /**
     * cierre diario
     */
    Z('Z'), 
    /**
     * cierre por cambio de cajero
     */
    X('X');
    
    char letra = 'Z';

    /**
     * abreviatura
     * @return 
     */
    public char getLetra() {
        return letra;
    }

    TipoCierre(char letra) {
        this.letra = letra;
    }

    /**
     * convierte abreviatura en TipoCierre
     * @param letra
     * @return 
     */
    public static TipoCierre parseString(String letra) {
        if (letra.toUpperCase().equals("Z")) {
            return TipoCierre.Z;
        }
        return TipoCierre.X;
    }
}
