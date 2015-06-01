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
public class ImprItemTique extends IfCommand {

    /**
     * Constructor
     */
    public ImprItemTique() {
        this.commandId = 0x42;
        this.name = "imprimirItemDeLineaEnTiqueFiscal";
        this.nombreAbreviado = "TIQUEITEM";
        this.descripcionComando = "No se aceptará el comando si no hay un comprobante fiscal abierto. Se lo rechazará si hay papel en la entrada para impresión o validación de hojas sueltas. Se rechazará si la acumulación de montos genera un desborde de totales. IMPORTANTE: Un ítem de línea no puede tener el ajuste de la base imponible e Impuestos Internos Fijos al mismo tiempo.";

        this.params.put(InParam.IIT__DESCRIPCION_PROD, "");

        this.params.put(InParam.IIT__CANT_UNIDADES, String.format("%08d",  0));

        this.params.put(InParam.IIT__PRECIO_UNITARIO, String.format("%012.4f", 0f).replace(',', '.')); // dependiendo el locale lo va a poner con comma y va siempre con punto

        this.params.put(InParam.IIT__TASA_IMPOSITIVA, String.format("%04d",  0));

        this.params.put(InParam.IIT__CALIF_ITEM, CalificadorItem.MONTO_AGREGADO_O_VENTA_SUMA.getLetra());

        this.params.put(InParam.IIT__CANTIDAD_BULTOS, String.format("%04d",  0));

        this.params.put(InParam.IIT__TASA_AJUSTE_VAR, String.format("%08d",  0));

        this.params.put(InParam.IIT__IMPUESTO_INTERNO, String.format("%07d",  0) + String.format("%08d",  0));
    }
    
    /**
     * Monto impuesto interno
     * @return 
     */
    public Double getMontoImpuestoInterno() {

        Double entero = Double.parseDouble(params.get(InParam.IIT__IMPUESTO_INTERNO).substring(0, 6));

        Double decimal = Double.parseDouble("0." + params.get(InParam.IIT__IMPUESTO_INTERNO).substring(7));

        return entero + decimal;
    }

    /**
     * Monto impuesto interno
     * @param montoImpuestoInterno 
     */
    public void setMontoImpuestoInterno(Double montoImpuestoInterno) {
        Double decimales = (montoImpuestoInterno - Math.floor(montoImpuestoInterno)) * 100000000;

        this.params.put(InParam.IIT__IMPUESTO_INTERNO, String.format("%07d",  montoImpuestoInterno.intValue()) + String.format("%08d",  decimales.intValue()));
    }

    /**
     * bultos
     * @return 
     */
    public Integer getBultos() {
        return Integer.parseInt(this.params.get(InParam.IIT__CANTIDAD_BULTOS));
    }

    /**
     * bultos
     * @param bultos 
     */
    public void setBultos(Integer bultos) {
        this.params.put(InParam.IIT__CANTIDAD_BULTOS, String.format("%05d",  bultos));
    }

    /**
     * calificador
     * @return 
     */
    public CalificadorItem getCalificador() {
        return CalificadorItem.parseString(params.get(InParam.IIT__CALIF_ITEM));
    }

    /**
     * calificador
     * @param calificadorItem 
     */
    public void setCalificador(CalificadorItem calificadorItem) {
        params.put(InParam.IIT__CALIF_ITEM, calificadorItem.getLetra());
    }

    /**
     * cantidad
     * @return 
     */
    public double getCantidad() {

        Double entero = Double.parseDouble(params.get(InParam.IIT__CANT_UNIDADES).substring(0, 4));

        Double decimal = Double.parseDouble("0." + params.get(InParam.IIT__CANT_UNIDADES).substring(5));

        return entero + decimal;
    }

    /**
     * cantidad
     * @param cantidad 
     */
    public void setCantidad(Double cantidad) {
        Double val = (cantidad - Math.floor(cantidad)) * 1000;
        this.params.put(InParam.IIT__CANT_UNIDADES, String.format("%05d",  cantidad.intValue()) + String.format("%03d",  val.intValue()));
    }

    /**
     * descripción producto
     * @return 
     */
    public String getDescripcionProducto() {
        return params.get(InParam.IIT__DESCRIPCION_PROD);
    }

    /**
     * Descripción producto
     * @param descripcionProducto 
     */
    public void setDescripcionProducto(String descripcionProducto) {
        params.put(InParam.IIT__DESCRIPCION_PROD, descripcionProducto);
    }

    /**
     * iva
     * @return 
     */
    public double getIva() {
        return Double.parseDouble(params.get(InParam.IIT__TASA_IMPOSITIVA)) / 100;
    }

    /**
     * iva
     * @param iva 
     */
    public void setIva(Double iva) {
        this.params.put(InParam.IIT__TASA_IMPOSITIVA, String.format("%04d",  ((Double) (iva * 100)).intValue()));
    }

    /**
     * precio unitario
     * @return 
     */
    public double getPrecioUnitario() {
        if (this.params.get(InParam.IIT__PRECIO_UNITARIO).contains(".")) {
            return Double.parseDouble(this.params.get(InParam.IIT__PRECIO_UNITARIO));
        }

        Double entero = Double.parseDouble(params.get(InParam.IIT__PRECIO_UNITARIO).substring(0, 6));

        Double decimal = Double.parseDouble("0." + params.get(InParam.IIT__PRECIO_UNITARIO).substring(7));

        return entero + decimal;
    }

    /**
     * precio unitario
     * @param precioUnitario 
     */
    public void setPrecioUnitario(Double precioUnitario) {
        Double val = (precioUnitario - Math.floor(precioUnitario)) * 10000;
        this.params.put(InParam.IIT__PRECIO_UNITARIO, String.format("%07d",  precioUnitario.intValue()) + "." + String.format("%04d",  val.intValue()));
    }

    /**
     * tasa ajuste variable
     * @return 
     */
    public double getTasaAjusteVariable() {
        return Double.parseDouble(this.params.get(InParam.IIT__TASA_AJUSTE_VAR)) / 100000000;
    }

    /**
     * tasa ajuste variable
     * @param tasaAjusteVariable 
     */
    public void setTasaAjusteVariable(Double tasaAjusteVariable) {
        this.params.put(InParam.IIT__TASA_AJUSTE_VAR, String.format("%08d",  ((Double) (tasaAjusteVariable * 100000000)).intValue()));
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

}
