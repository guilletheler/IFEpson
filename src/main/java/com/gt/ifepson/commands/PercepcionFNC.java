/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;

/**
 *
 * @author guillermot
 */
public class PercepcionFNC extends IfCommand {

    /**
     * descripcion percepcion
     * @return 
     */
    public String getDescripcionPercepcion() {
        return params.get(InParam.PFNC__DESCRIPCION);
    }

    /**
     * descripción percepcion
     * @param descripcionPercepcion 
     */
    public void setDescripcionPercepcion(String descripcionPercepcion) {
        params.put(InParam.PFNC__DESCRIPCION, descripcionPercepcion);
    }

    /**
     * monto
     * @return 
     */
    public double getMonto() {
        return Double.parseDouble(this.params.get(InParam.PFNC__MONTO_PERCEPCION)) / 100;
    }

    /**
     * monto
     * @param monto 
     */
    public void setMonto(double monto) {
        this.params.put(InParam.PFNC__MONTO_PERCEPCION, String.format("%010d", ((Double) (monto * 100)).intValue()));
    }

    /**
     * tasa
     * @return 
     */
    public double getTasa() {
        return Double.parseDouble(this.params.get(InParam.PFNC__TASA_IVA)) / 100;
    }

    /**
     * tasa
     * @param tasa 
     */
    public void setTasa(double tasa) {
        this.params.put(InParam.PFNC__TASA_IVA, String.format("%04d", ((Double) (tasa * 100)).intValue()));
    }

    /**
     * tipo percepcion
     * @return 
     */
    public TipoPercepcion getTipo() {
        return TipoPercepcion.parseString(this.params.get(InParam.PFNC__TIPO_PERCEPCION));
    }

    /**
     * tipo percepcion
     * @param tipo 
     */
    public void setTipo(TipoPercepcion tipo) {
        this.params.put(InParam.PFNC__TIPO_PERCEPCION, tipo.getLetra());
    }

    /**
     * constructor
     */
    public PercepcionFNC() {
        this.commandId = 0x66;
        this.name = "percepcionFNC";
        this.descripcionComando = "Se rechazará este comando si no hay una Factura, Nota de Crédito, Tique-Factura o Tique-Nota de Crédito abierto y al menos un ítem de venta facturado o si los montos acumulados generan un desbordamiento de total. Se usa este comando para imprimir información sobre percepciones Globales o de IVA. Si se envía una Percepción de IVA y no se han facturado productos a dicha tasa, el comando será rechazado. Importante: Las percepciones no van impresas entre productos facturados. Las percepciones se imprimen por descripción en el cierre de la Factura, Nota de Crédito, Tique-Factura ó Tique-Nota de Crédito y en el Cierre Z.";
        this.nombreAbreviado = "FACTPERCEP";

        this.params.put(InParam.PFNC__DESCRIPCION, "");
        this.params.put(InParam.PFNC__TIPO_PERCEPCION, TipoPercepcion.OtroTipo.getLetra());
        this.params.put(InParam.PFNC__MONTO_PERCEPCION, String.format("%010d", 0));
        this.params.put(InParam.PFNC__TASA_IVA, String.format("%04d", 0));
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

    /**
     * tipo de percepción
     */
    public enum TipoPercepcion {

        /**
         * otro tipo
         */
        OtroTipo("O"),
        /**
         * Global iva
         */
        GlobalIVA("I"),
        /**
         * IVA tasa determinada
         */
        IVATasaDeterminada("T");
        
        String letra = "";

        TipoPercepcion(String letra) {
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
         * Abreviatura a TipoPercepción
         * @param letra
         * @return 
         */
        public static TipoPercepcion parseString(String letra) {
            switch (letra.charAt(0)) {
                case 'O':
                    return TipoPercepcion.OtroTipo;
                case 'I':
                    return TipoPercepcion.GlobalIVA;
                case 'T':
                    return TipoPercepcion.IVATasaDeterminada;
            }
            return null;
        }
    }
}
