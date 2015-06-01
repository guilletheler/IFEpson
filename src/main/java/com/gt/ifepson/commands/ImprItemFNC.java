/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.commands.enums.CalificadorItem;
import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;

/**
 *
 * @author guillermot
 */
public class ImprItemFNC extends IfCommand {

    /**
     * monto impuesto interno
     * @return 
     */
    public Double getMontoImpuestoInterno() {

        Double entero = Double.parseDouble(params.get(InParam.IIFNC__MONTO_IMP_INT_FIJO).substring(0, 6));

        Double decimal = Double.parseDouble("0." + params.get(InParam.IIFNC__MONTO_IMP_INT_FIJO).substring(7));

        return entero + decimal;
    }

    /**
     * monto impuesto interno
     * @param montoImpuestoInterno 
     */
    public void setMontoImpuestoInterno(Double montoImpuestoInterno) {
        Double val = (montoImpuestoInterno - Math.floor(montoImpuestoInterno)) * 100000000;

        this.params.put(InParam.IIFNC__MONTO_IMP_INT_FIJO, String.format("%07d", montoImpuestoInterno.intValue()) + String.format("%08d", val.intValue()));
    }

    /**
     * bultos
     * @return 
     */
    public Integer getBultos() {
        return Integer.parseInt(this.params.get(InParam.IIFNC__CANTIDAD_BULTOS));
    }

    /**
     * bultos
     * @param bultos 
     */
    public void setBultos(Integer bultos) {
        this.params.put(InParam.IIFNC__CANTIDAD_BULTOS, String.format("%05d",  bultos));
    }

    /**
     * Calificador item
     * @return 
     */
    public CalificadorItem getCalificadorItem() {
        return CalificadorItem.parseString(params.get(InParam.IIFNC__CALIF_ITEM));
    }

    /**
     * Calificador item
     * @param calificadorItem 
     */
    public void setCalificador(CalificadorItem calificadorItem) {
        params.put(InParam.IIFNC__CALIF_ITEM, calificadorItem.getLetra());
    }

    /**
     * cantidad
     * @return 
     */
    public double getCantidad() {

        Double entero = Double.parseDouble(params.get(InParam.IIFNC__CANT_UNIDADES).substring(0, 4));

        Double decimal = Double.parseDouble("0." + params.get(InParam.IIFNC__CANT_UNIDADES).substring(5));

        return entero + decimal;
    }

    /**
     * cantidad
     * @param cantidad 
     */
    public void setCantidad(Double cantidad) {
        Double val = (cantidad - Math.floor(cantidad)) * 1000;
        this.params.put(InParam.IIFNC__CANT_UNIDADES, String.format("%05d",  cantidad.intValue()) + String.format("%03d",  val.intValue()));
    }

    /**
     * Descripción extra linea 1
     * @return 
     */
    public String getDescripcionExtra1() {
        return params.get(InParam.IIFNC__DESCRIP_EXTRA_1);
    }

    /**
     * Descripción extra linea 1
     * @param descripcionExtra1 
     */
    public void setDescripcionExtra1(String descripcionExtra1) {
        params.put(InParam.IIFNC__DESCRIP_EXTRA_1, descripcionExtra1);
    }

    /**
     * Descripción extra linea 2
     * @return 
     */
    public String getDescripcionExtra2() {
        return params.get(InParam.IIFNC__DESCRIP_EXTRA_2);
    }

    /**
     * +Descripción extra linea 2
     * @param descripcionExtra2 
     */
    public void setDescripcionExtra2(String descripcionExtra2) {
        params.put(InParam.IIFNC__DESCRIP_EXTRA_2, descripcionExtra2);
    }

    /**
     * Descripción extra linea 3
     * @return 
     */
    public String getDescripcionExtra3() {
        return params.get(InParam.IIFNC__DESCRIP_EXTRA_3);
    }

    /**
     * Descripción extra linea 3
     * @param descripcionExtra3 
     */
    public void setDescripcionExtra3(String descripcionExtra3) {
        params.put(InParam.IIFNC__DESCRIP_EXTRA_3, descripcionExtra3);
    }

    /**
     * Descripción del producto
     * 
     * @return 
     */
    public String getDescripcionProducto() {
        return params.get(InParam.IIFNC__DESCRIPCION_PROD);
    }

    /**
     * Descripción del producto
     * @param descripcionProducto 
     */
    public void setDescripcion(String descripcionProducto) {
        params.put(InParam.IIFNC__DESCRIPCION_PROD, descripcionProducto);
    }

    /**
     * iva
     * @return 
     */
    public double getIva() {
        return Double.parseDouble(params.get(InParam.IIFNC__TASA_IVA)) / 100;
    }

    /**
     * iva
     * @param iva 
     */
    public void setIva(double iva) {
        this.params.put(InParam.IIFNC__TASA_IVA, String.format("%04d",  ((Double) (iva * 100)).intValue()));
    }

    /**
     * precio unitario
     * @return 
     */
    public double getPrecioUnitario() {
        if (this.params.get(InParam.IIFNC__PRECIO_UNITARIO).contains(".")) {
            return Double.parseDouble(this.params.get(InParam.IIFNC__PRECIO_UNITARIO));
        }

        return Double.parseDouble(this.params.get(InParam.IIFNC__PRECIO_UNITARIO));
    }

    /**
     * precio unitario
     * @param precioUnitario 
     */
    public void setPrecioUnitario(Double precioUnitario) {
        Double val = (precioUnitario - Math.floor(precioUnitario)) * 10000;
        this.params.put(InParam.IIFNC__PRECIO_UNITARIO, String.format("%07d",  precioUnitario.intValue()) + "." + String.format("%04d",  val.intValue()));
    }

    /**
     * tasa acresentamiento
     * @return 
     */
    public Double getTasaAcresentamiento() {
        return Double.parseDouble(this.params.get(InParam.IIFNC__TASA_ACRECENTAM)) / 100;
    }

    /**
     * tasa acresentamiento
     * @param tasaAcresentamiento 
     */
    public void setTasaAcresentamiento(Double tasaAcresentamiento) {
        this.params.put(InParam.IIFNC__TASA_ACRECENTAM, String.format("%04d",  ((Double) (tasaAcresentamiento * 100)).intValue()));
    }

    /**
     * tasa ajuste variable
     * @return 
     */
    public double getTasaAjusteVariable() {
        return Double.parseDouble(this.params.get(InParam.IIFNC__TASA_AJUSTE_VAR)) /  100000000;
    }

    /**
     * tasa ajuste variable
     * @param tasaAjusteVariable 
     */
    public void setTasaAjusteVariable(double tasaAjusteVariable) {
        this.params.put(InParam.IIFNC__TASA_AJUSTE_VAR, String.format("%08d", ((Double) (tasaAjusteVariable * 100000000)).intValue()));
    }

    /**
     * constructor
     */
    public ImprItemFNC() {
        this.commandId = 0x62;
        this.name = "ImprmirItemFactura_NotaCredito_TF_TNC";
        this.descripcionComando = "No se aceptará el comando si no hay un comprobante fiscal abierto. Se lo rechazará si no hay papel en la entrada para impresión o validación de hojas sueltas.";
        this.nombreAbreviado = "FACTITEM";

        this.params.put(InParam.IIFNC__DESCRIPCION_PROD, "");

        this.params.put(InParam.IIFNC__CANT_UNIDADES, String.format("%05d",  0) + String.format("%03d",  0));

        this.params.put(InParam.IIFNC__PRECIO_UNITARIO, String.format("%07d",  0) + "." + String.format("%04d",  0));

        this.params.put(InParam.IIFNC__TASA_IVA, String.format("%04d",  2100));

        this.params.put(InParam.IIFNC__CALIF_ITEM, CalificadorItem.MONTO_AGREGADO_O_VENTA_SUMA.getLetra());

        this.params.put(InParam.IIFNC__CANTIDAD_BULTOS, String.format("%05d",  0));

        this.params.put(InParam.IIFNC__TASA_AJUSTE_VAR, String.format("%08d",  0));

        this.params.put(InParam.IIFNC__DESCRIP_EXTRA_1, "");

        this.params.put(InParam.IIFNC__DESCRIP_EXTRA_2, "");

        this.params.put(InParam.IIFNC__DESCRIP_EXTRA_3, "");

        this.params.put(InParam.IIFNC__TASA_ACRECENTAM, String.format("%04d",  0));

        this.params.put(InParam.IIFNC__MONTO_IMP_INT_FIJO, String.format("%07d",  0) + String.format("%08d",  0));


    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

    
}
