/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands.enums;

/**
     * Letra del documento
     */
    public enum LetraDocumento {

        /**
         * A
         */
        A("A"),
        /**
         * B
         */
        B("B"),
        /**
         * C
         */
        C("C"),
        /**
         * X
         */
        X("X");
        
        String letra = "";

        LetraDocumento(String letra) {
            this.letra = letra;
        }

        /**
         * abreviatura
         * @return 
         */
        public String getLetra() {
            return letra;
        }

        /**
         * Convierte la abreviatura en LetraDocumento
         * @param letra
         * @return 
         */
        public static LetraDocumento parseLetra(String letra) {
            switch (letra.charAt(0)) {
                case 'A':
                    return LetraDocumento.A;
                case 'B':
                    return LetraDocumento.B;
                case 'C':
                    return LetraDocumento.C;
                case 'X':
                    return LetraDocumento.X;
            }
            return null;
        }
    }