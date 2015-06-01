/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

/** 
     * calificador del item
     */
    public enum CalificadorItem {

        /**
         * monto agregado o venta (suma)
         */
        MONTO_AGREGADO_O_VENTA_SUMA("M"),
        /**
         * Anula item(resta)
         */
        ANULA_ITEM_RESTA("m"),
        /**
         * Bonificación (Resta)
         */
        BONIFICACION_RESTA("R"),
        /**
         * Anula Bonificacion (Suma)
         */
        ANULA_BONIFICACION("r");

        CalificadorItem(String letra) {
            this.letra = letra;
        }
        
        String letra = "";

        /**
         * Abreviatura
         * @return 
         */
        public String getLetra() {
            return letra;
        }

        /**
         * Convierte abreviatura en CalificadorItem
         * @param letra
         * @return 
         */
        public static CalificadorItem parseString(String letra) {

            switch (letra.charAt(0)) {
                case 'M':
                    return CalificadorItem.MONTO_AGREGADO_O_VENTA_SUMA;
                case 'm':
                    return CalificadorItem.ANULA_ITEM_RESTA;
                case 'R':
                    return CalificadorItem.BONIFICACION_RESTA;
                case 'r':
                    return CalificadorItem.ANULA_BONIFICACION;
            }

            return null;
        }
    }