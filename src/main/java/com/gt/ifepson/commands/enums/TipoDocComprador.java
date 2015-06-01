/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

/**
 * Tipo de documento del comprador
 *
 * @author guillermot
 */
public enum TipoDocComprador {

    /**
     * CUIT
     */
    CUIT("CUIT"),
    /**
     * CUIL
     */
    CUIL("CUIL");

    String tipo = "";

    TipoDocComprador(String tipo) {
        this.tipo = tipo;
    }

    /**
     * tipo
     *
     * @return
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * convierte un string a TipoComprador
     *
     * @param letra
     * @return
     */
    public static TipoDocComprador parseString(String letra) {

        if (letra.equalsIgnoreCase("CUIT")) {
            return TipoDocComprador.CUIT;
        }

        return TipoDocComprador.CUIL;
    }
}
