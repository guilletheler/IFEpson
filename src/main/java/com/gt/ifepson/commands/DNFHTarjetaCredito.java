/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Documento no fiscal homologado<br/>
 * tarjeta de credito
 *
 * @author guillermot
 */
public class DNFHTarjetaCredito extends IfCommand {

    /**
     * Obtiene el parametro codigo de autorizacion
     *
     * @return
     */
    public String getCodigoAutorizacion() {
        return params.get(InParam.CNFHTC__COD_AUTORIZ);
    }

    /**
     * Setea el parametro codigo de autorizacion
     *
     * @param codigoAutorizacion
     */
    public void setCodigoAutorizacion(String codigoAutorizacion) {
        params.put(InParam.CNFHTC__COD_AUTORIZ, getStr(codigoAutorizacion));
    }

    /**
     * Obtiene el parametro cantidad de cuotas
     *
     * @return
     */
    public String getCuotas() {
        return params.get(InParam.CNFHTC__CANT_CUOTAS);
    }

    /**
     * Setea el parametro cantidad de cuotas
     *
     * @param cuotas
     */
    public void setCuotas(String cuotas) {
        params.put(InParam.CNFHTC__CANT_CUOTAS, getStr(cuotas));
    }

    /**
     * Obtiene el parametro importe
     *
     * @return
     */
    public Double getImporte() {
        return Double.parseDouble(this.params.get(InParam.CNFHTC__IMPORTE)) / 100;
    }

    /**
     * Setea el parametro importe
     *
     * @param importe
     */
    public void setImporte(Double importe) {
        //this.params.put(InParam.CNFHTC__IMPORTE, GeneralFunc.stringFormat("{0:00000000000}", importe * 100));
        this.params.put(InParam.CNFHTC__IMPORTE, String.format("%011d", ((Double) (importe * 100)).intValue()));
    }

    /**
     * Indica si es linea de aclaración
     *
     * @return
     */
    public boolean isLineaAclaracion() {
        return this.params.get(InParam.CNFHTC__LINEA_ACLARA).equals("P");
    }

    /**
     * Establece que es una linea de aclaracion
     *
     * @param lineaAclaracion
     */
    public void setLineaAclaracion(boolean lineaAclaracion) {
        this.params.put(InParam.CNFHTC__LINEA_ACLARA, lineaAclaracion ? "P" : "");
    }

    /**
     * Indica si es una linea de firma
     *
     * @return
     */
    public boolean isLineaFirma() {
        return this.params.get(InParam.CNFHTC__LINEA_FIRMA).equals("P");
    }

    /**
     * Establece que es una linea de firma
     *
     * @param lineaFirma
     */
    public void setLineaFirma(boolean lineaFirma) {
        this.params.put(InParam.CNFHTC__LINEA_FIRMA, lineaFirma ? "P" : "");
    }

    /**
     * Obtiene la moneda
     *
     * @return
     */
    public String getMoneda() {
        return params.get(InParam.CNFHTC__MONEDA);
    }

    /**
     * Establece la moneda
     *
     * @param moneda
     */
    public void setMoneda(String moneda) {
        this.params.put(InParam.CNFHTC__MONEDA, getStr(moneda));
    }

    /**
     * Obtiene el nombre de la tarjeta
     *
     * @return
     */
    public String getNombreTarjeta() {
        return params.get(InParam.CNFHTC__NOMBRE_TARJETA);
    }

    /**
     * Establece el nombre de la tarjeta
     *
     * @param nombreTarjeta
     */
    public void setNombreTarjeta(String nombreTarjeta) {
        params.put(InParam.CNFHTC__NOMBRE_TARJETA, getStr(nombreTarjeta));
    }

    /**
     * Obtiene el numero de cupon
     *
     * @return
     */
    public String getNroCupon() {
        return params.get(InParam.CNFHTC__NRO_CUPON);
    }

    /**
     * Obtiene el numero de cupon
     *
     * @param nroCupon
     */
    public void setNroCupon(String nroCupon) {
        params.put(InParam.CNFHTC__NRO_CUPON, getStr(nroCupon));
    }

    /**
     * Obtiene el nro del establecimiento
     *
     * @return
     */
    public String getNroEstablecimiento() {
        return params.get(InParam.CNFHTC__NRO_ESTAB);
    }

    /**
     * Setea el nro del establecimiento
     *
     * @param nroEstablecimiento
     */
    public void setNroEstablecimiento(String nroEstablecimiento) {
        params.put(InParam.CNFHTC__NRO_ESTAB, getStr(nroEstablecimiento));
    }

    /**
     * Obtiene el nro de factura
     *
     * @return
     */
    public String getNroFactura() {
        return params.get(InParam.CNFHTC__NRO_FACTURA);
    }

    /**
     * Setea el nro de factura
     *
     * @param nroFactura
     */
    public void setNroFactura(String nroFactura) {
        params.put(InParam.CNFHTC__NRO_FACTURA, getStr(nroFactura));
    }

    /**
     * Obtiene el nro interno
     *
     * @return
     */
    public String getNroInterno() {
        return params.get(InParam.CNFHTC__NRO_INT_COMP);
    }

    /**
     * Setea el nro interno
     *
     * @param nroInterno
     */
    public void setNroInterno(String nroInterno) {
        params.put(InParam.CNFHTC__NRO_INT_COMP, getStr(nroInterno));
    }

    /**
     * Obtiene el nro de lote
     *
     * @return
     */
    public String getNroLote() {
        return params.get(InParam.CNFHTC__NRO_LOTE);
    }

    /**
     * Setea el nro de lote
     *
     * @param nroLote
     */
    public void setNroLote(String nroLote) {
        params.put(InParam.CNFHTC__NRO_LOTE, getStr(nroLote));
    }

    /**
     * Obtiene el nro de sucursal
     *
     * @return
     */
    public String getNroSucursal() {
        return params.get(InParam.CNFHTC__NRO_SUCURSAL);
    }

    /**
     * Setea el nro de sucursal
     *
     * @param nroSucursal
     */
    public void setNroSucursal(String nroSucursal) {
        params.put(InParam.CNFHTC__NRO_SUCURSAL, getStr(nroSucursal));
    }

    /**
     * Obtiene el nro de tarjeta
     *
     * @return
     */
    public String getNroTarjeta() {
        return params.get(InParam.CNFHTC__NRO_TARJETA);
    }

    /**
     * Setea el nro de tarjeta
     *
     * @param nroTarjeta
     */
    public void setNroTarjeta(String nroTarjeta) {
        params.put(InParam.CNFHTC__NRO_TARJETA, getStr(nroTarjeta));
    }

    /**
     * Obtiene el nro de terminal electrónica
     *
     * @return
     */
    public String getNroTerminalElectronica() {
        return params.get(InParam.CNFHTC__NRO_TERM_ELEC);
    }

    /**
     * Setea el nro de terminal electrónica
     *
     * @param nroTerminalElectronica
     */
    public void setNroTerminalElectronica(String nroTerminalElectronica) {
        params.put(InParam.CNFHTC__NRO_TERM_ELEC, getStr(nroTerminalElectronica));
    }

    /**
     * Obtiene el operador
     *
     * @return
     */
    public String getOperador() {
        return params.get(InParam.CNFHTC__OPERADOR);
    }

    /**
     * Setea el operador
     *
     * @param operador
     */
    public void setOperador(String operador) {
        params.put(InParam.CNFHTC__OPERADOR, getStr(operador));
    }

    /**
     * Obtiene el nro de terminal
     *
     * @return
     */
    public String getTerminal() {
        return params.get(InParam.CNFHTC__NRO_TERMINAL);
    }

    /**
     * Setea el nro de terminal
     *
     * @param terminal
     */
    public void setTerminal(String terminal) {
        params.put(InParam.CNFHTC__NRO_TERMINAL, getStr(terminal));
    }

    /**
     * Obtiene el tipo de operacion
     *
     * @return
     */
    public String getTipoOperacion() {
        return params.get(InParam.CNFHTC__TIPO_OPERAC);
    }

    /**
     * Setea el tipo de operacion
     *
     * @param tipoOperacion
     */
    public void setTipoOperacion(String tipoOperacion) {
        params.put(InParam.CNFHTC__TIPO_OPERAC, getStr(tipoOperacion));
    }

    /**
     * Obtiene el usuario de la tarjeta
     *
     * @return
     */
    public String getUsuarioTarjeta() {
        return params.get(InParam.CNFHTC__USUARIO);
    }

    /**
     * Setea el usuario de la tarjeta
     *
     * @param usuarioTarjeta
     */
    public void setUsuarioTarjeta(String usuarioTarjeta) {
        params.put(InParam.CNFHTC__USUARIO, getStr(usuarioTarjeta));
    }

    /**
     * Obtiene la fecha de vencimiento de la tarjeta
     * @return 
     */
    public Date getVencimientoTarjeta() {
        try {

            return IfCommand.parseDate(params.get(InParam.CNFHTC__VENC_TARJ), "yyMMdd");

        } catch (ParseException ex) {
            Logger.getLogger(DNFHTarjetaCredito.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Setea la fecha de vencimiento de la tarjeta
     * @param vencimientoTarjeta 
     */
    public void setVencimientoTarjeta(Date vencimientoTarjeta) {
        params.put(InParam.CNFHTC__VENC_TARJ, String.format("%1$ty%1$tm%1$td", vencimientoTarjeta));
    }

    /**
     * Indica si es linea de telefono
     * @return 
     */
    public boolean isLineaTelefono() {
        return params.get(InParam.CNFHTC__LINEA_TELEFONO).equals("P");
    }

    /**
     * Setea que es linea de telefono
     * @param lineaTelefono 
     */
    public void setLineaTelefono(boolean lineaTelefono) {
        params.put(InParam.CNFHTC__LINEA_TELEFONO, lineaTelefono ? "P" : "");
    }

    /**
     * Constructor por defecto
     */
    public DNFHTarjetaCredito() {
        this.commandId = 0x4F;
        this.name = "DNFHTarjetaCredito";
        this.descripcionComando = "Comando Voucher Tarjeta de Crédito generado con un comprobante no fiscal homologado.";

        this.params.put(InParam.CNFHTC__01, "01");
        this.params.put(InParam.CNFHTC__NOMBRE_TARJETA, getStr(""));
        this.params.put(InParam.CNFHTC__NRO_TARJETA, getStr(""));
        this.params.put(InParam.CNFHTC__USUARIO, getStr(""));
        this.params.put(InParam.CNFHTC__VENC_TARJ, String.format("%1$ty%1$tm%1$td", Calendar.getInstance()));
        this.params.put(InParam.CNFHTC__NRO_ESTAB, getStr(""));
        this.params.put(InParam.CNFHTC__NRO_CUPON, getStr(""));
        this.params.put(InParam.CNFHTC__NRO_INT_COMP, getStr(""));
        this.params.put(InParam.CNFHTC__COD_AUTORIZ, getStr(""));
        this.params.put(InParam.CNFHTC__TIPO_OPERAC, getStr(""));
        this.params.put(InParam.CNFHTC__IMPORTE, String.format("%011d", 0));
        this.params.put(InParam.CNFHTC__CANT_CUOTAS, getStr(""));
        this.params.put(InParam.CNFHTC__MONEDA, getStr(""));
        this.params.put(InParam.CNFHTC__NRO_TERMINAL, getStr(""));
        this.params.put(InParam.CNFHTC__NRO_LOTE, getStr(""));
        this.params.put(InParam.CNFHTC__NRO_TERM_ELEC, getStr(""));
        this.params.put(InParam.CNFHTC__NRO_SUCURSAL, getStr(""));
        this.params.put(InParam.CNFHTC__OPERADOR, getStr(""));
        this.params.put(InParam.CNFHTC__NRO_FACTURA, this.getStr(""));
        this.params.put(InParam.CNFHTC__LINEA_FIRMA, "P");
        this.params.put(InParam.CNFHTC__LINEA_ACLARA, "P");
        this.params.put(InParam.CNFHTC__LINEA_TELEFONO, "P");
    }

    /**
     * En caso que <code>texto</code> no sea vacio devuelve texto<br/>
     * caso contrario devuelve una cadena iniciada por 0x7F;
     * @param texto 
     * @return 
     */
    private String getStr(String texto) {
        if (texto.trim().length() > 0) {
            return texto;
        }

        StringBuilder sb = new StringBuilder();

        sb.append((char) 0x7F);

        return sb.toString();
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }
}
