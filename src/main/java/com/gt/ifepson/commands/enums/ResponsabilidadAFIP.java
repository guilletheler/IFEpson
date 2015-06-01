/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

/**
 *
 * @author guillermot
 */
public enum ResponsabilidadAFIP {

    /**
     * Responsable inscripto
     */
    RESPONSABLE_INSCRIPTO("I", true, true),
    /**
     * Responsable no inscripto
     */
    RESPONSABLE_NO_INSCRIPTO("R", true, true),
    /**
     * no Responsable
     */
    NO_RESPONSABLE("N", true, true),
    /**
     * Excento
     */
    EXCENTO("E", true, true),
    /**
     * Monotributo
     */
    MONOTRIBUTO("M", true, true),
    /**
     * Consumidor final
     */
    CONSUMIDOR_FINAL("F", false, true),
    /**
     * Sujeto no categorizado
     */
    SUJETO_NO_CATEGORIZADO("S", false, true),
    /**
     * Monotributista social
     */
    MONOTRIBUTISTA_SOCIAL("T", true, true),
    /**
     * Puqueño contribuidor eventual
     */
    PEQUE_CONTRIB_EVENTUAL("C", false, true),
    /**
     * Puqueño contribuidor eventual social
     */
    PEQUE_CONTRIB_EVENTUAL_SOCIAL("V", false, true);

    String letra = "";
    boolean puedeVendedor = false;
    boolean puedeComprador = true;

    ResponsabilidadAFIP(String letra, boolean puedeVendedor, boolean puedeComprador) {
        this.letra = letra;
        this.puedeComprador = puedeComprador;
        this.puedeVendedor = puedeVendedor;
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
     * Puede usarse como vendedor
     *
     * @return
     */
    public boolean isPuedeVendedor() {
        return puedeVendedor;
    }

    /**
     * Puede usarse como comprador
     *
     * @return
     */
    public boolean isPuedeComprador() {
        return puedeComprador;
    }

    /**
     * Convierte la abreviatura en ResponsabilidadAfip
     *
     * @param letra
     * @return
     */
    public static ResponsabilidadAFIP parseLetra(String letra) {
        switch (letra.charAt(0)) {
            case 'I':
                return ResponsabilidadAFIP.RESPONSABLE_INSCRIPTO;
            case 'R':
                return ResponsabilidadAFIP.RESPONSABLE_NO_INSCRIPTO;
            case 'N':
                return ResponsabilidadAFIP.NO_RESPONSABLE;
            case 'E':
                return ResponsabilidadAFIP.EXCENTO;
            case 'M':
                return ResponsabilidadAFIP.MONOTRIBUTO;
            case 'F':
                return ResponsabilidadAFIP.CONSUMIDOR_FINAL;
            case 'S':
                return ResponsabilidadAFIP.SUJETO_NO_CATEGORIZADO;
            case 'T':
                return ResponsabilidadAFIP.MONOTRIBUTISTA_SOCIAL;
            case 'C':
                return ResponsabilidadAFIP.PEQUE_CONTRIB_EVENTUAL;
            case 'V':
                return ResponsabilidadAFIP.PEQUE_CONTRIB_EVENTUAL_SOCIAL;
        }

        return null;
    }
}
