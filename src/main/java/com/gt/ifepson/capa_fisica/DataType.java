/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.capa_fisica;

/**
 * Tipos de dato soportados por el controlador fiscal
 *
 * @author guillermot
 */
public enum DataType {

    /**
     * Alfanumerico
     */
    Alfa("A", "Alfanumerico"),
    /**
     * Numero Entero
     */
    Integer("I", "Numero Entero"),
    /**
     * Numero con 8 decimales
     */
    Num8Dec("W", "Numero con 8 decimales"),
    /**
     * Numero con 4 decimales
     */
    Num4Dec("N", "Numero con 4 decimales"),
    /**
     * Numero con 3 decimales
     */
    Num3Dec("M", "Numero con 3 decimales"),
    /**
     * Numero con 2 decimales
     */
    Num2Dec("D", "Numero con 2 decimales"),
    /**
     * Numero booleano de 1 o mas de largo
     */
    Boolean("B", "Numero booleano de 1 o mas de largo"),
    /**
     * Hexadecimal
     */
    Hexa("H", "Hexadecimal"),
    /**
     * Byte, debe tomarse el valor numerico del byte
     */
    Byte("Y", "Byte, debe tomarse el valor numerico del byte"),
    /**
     * Hora, el formato es HHMMSS
     */
    Hora("T", "Hora, el formato es HHMMSS"),
    /**
     * Fecha, el formato es AAMMDD
     */
    Fecha("F", "Fecha, el formato es AAMMDD");

    String type = "A";
    String desc = "";

    DataType(String letter, String desc) {
        type = letter;
        this.desc = desc;
    }

    /**
     * Descripción
     * @return 
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Tipo
     * @return 
     */
    public String getType() {
        return type;
    }
}
