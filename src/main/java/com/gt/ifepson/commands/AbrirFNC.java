/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.commands.enums.LetraDocumento;
import com.gt.ifepson.commands.enums.FormatoDatos;
import com.gt.ifepson.commands.enums.TipoComprobante;
import com.gt.ifepson.IFException;
import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;
import com.gt.ifepson.commands.enums.DensidadImpresion;
import com.gt.ifepson.commands.enums.ResponsabilidadAFIP;
import com.gt.ifepson.commands.enums.TipoDocComprador;
import com.gt.ifepson.commands.enums.TipoFormulario;
import com.gt.ifepson.commands.enums.TipoSalida;

/**
 * Comando Abrir Factura o Nota de Credito
 * @author guillermot
 */
public class AbrirFNC extends IfCommand {

    /**
     * Constructor
     */
    public AbrirFNC() {
        this.commandId = 0x60;
        this.name = "AbrirComprobanteFactura_NotaCredito_TF_TNC";
        this.nombreAbreviado = "FACTABRE";
        this.descripcionComando = "Este comando es el primer paso para producir un Comprobante Fiscal tipo Factura, Nota de Crédito, Tique-Factura (TF) o Tique-Nota de Crédito (TNC) (según modelo del equipo). Se rechazará el comando si hay otro comprobante fiscal abierto.";

        this.params.put(InParam.AFNC__TIPO_DOC_FISCAL, TipoComprobante.FACTURA.getLetra());
        this.params.put(InParam.AFNC__TIPO_SALIDA_IMPRESA, TipoSalida.FORMULARIO_CONTINUO.getLetra());
        this.params.put(InParam.AFNC__LETRA_DOCUMENTO, LetraDocumento.A.getLetra());
        this.params.put(InParam.AFNC__CANT_COPIAS, "1");
        this.params.put(InParam.AFNC__TIPO_FORMULARIO, TipoFormulario.PRE_IMPRESO.getLetra());
        this.params.put(InParam.AFNC__DENSIDAD_LETRA, DensidadImpresion.CPI12.getCpi());
        this.params.put(InParam.AFNC__RESP_AFIP_VENDEDOR, ResponsabilidadAFIP.RESPONSABLE_INSCRIPTO.getLetra());
        this.params.put(InParam.AFNC__RESP_AFIP_COMPRADOR, ResponsabilidadAFIP.CONSUMIDOR_FINAL.getLetra());
        this.params.put(InParam.AFNC__NOMBRE_COMPRAD_L1, IfCommand.bytesPrimitiveToString(new byte[] {(byte) 0x7F}));
        this.params.put(InParam.AFNC__NOMBRE_COMPRAD_L2, IfCommand.bytesPrimitiveToString(new byte[] {(byte) 0x7F}));
        this.params.put(InParam.AFNC__TIPO_DOC_COMPRADOR, TipoDocComprador.CUIT.getTipo());
        this.params.put(InParam.AFNC__CUIT_COMPRADOR, IfCommand.bytesPrimitiveToString(new byte[] {(byte) 0x7F}));
        this.params.put(InParam.AFNC__LEYENDA_BIEN_USO, "N");
        this.params.put(InParam.AFNC__DOMIC_COMPRADOR_L1, IfCommand.bytesPrimitiveToString(new byte[] {(byte) 0x7F}));
        this.params.put(InParam.AFNC__DOMIC_COMPRADOR_L2, IfCommand.bytesPrimitiveToString(new byte[] {(byte) 0x7F}));
        this.params.put(InParam.AFNC__DOMIC_COMPRADOR_L3, IfCommand.bytesPrimitiveToString(new byte[] {(byte) 0x7F}));
        this.params.put(InParam.AFNC__REMITOS_RELAC_L1, IfCommand.bytesPrimitiveToString(new byte[] {(byte) 0x7F}));
        this.params.put(InParam.AFNC__REMITOS_RELAC_L2, IfCommand.bytesPrimitiveToString(new byte[] {(byte) 0x7F}));
        this.params.put(InParam.AFNC__FORMATO_ALMAC_DAT, FormatoDatos.NO_DNFH.getLetra());
    }
    
    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }
    
    /**
     * cuit del comprador
     * @return 
     */
    public String getCUIT_Comprador() {
        return params.get(InParam.AFNC__CUIT_COMPRADOR);
    }

    /**
     * cuit del comprador
     * @param CUIT_Comprador 
     */
    public void setCUIT_Comprador(String CUIT_Comprador) {
        params.put(InParam.AFNC__CUIT_COMPRADOR, CUIT_Comprador);
    }

    /**
     * comprador, linea 1
     * @return 
     */
    public String getComprador_linea1() {
        return params.get(InParam.AFNC__NOMBRE_COMPRAD_L1);
    }

    /**
     * comprador, linea 1
     * @param Comprador_linea1 
     */
    public void setComprador_linea1(String Comprador_linea1) {
        params.put(InParam.AFNC__NOMBRE_COMPRAD_L1, Comprador_linea1);
    }

    /**
     * comprador, linea 2
     * @return 
     */
    public String getComprador_linea2() {
        return params.get(InParam.AFNC__NOMBRE_COMPRAD_L2);
    }

    /**
     * comprador, linea 2
     * @param Comprador_linea2 
     */
    public void setComprador_linea2(String Comprador_linea2) {
        params.put(InParam.AFNC__NOMBRE_COMPRAD_L2, Comprador_linea2);
    }
    
    /**
     * domicilio comprador, linea 1
     * @return 
     */
    public String getDomicilioComprador_linea1() {
        return params.get(InParam.AFNC__DOMIC_COMPRADOR_L1);
    }

    /**
     * domicilio comprador, linea 1
     * @param DomicilioComprador_linea1 
     */
    public void setDomicilioComprador_linea1(String DomicilioComprador_linea1) {
        params.put(InParam.AFNC__DOMIC_COMPRADOR_L1, DomicilioComprador_linea1);
    }

    /**
     * domicilio comprador, linea 2
     * @return 
     */
    public String getDomicilioComprador_linea2() {
        return params.get(InParam.AFNC__DOMIC_COMPRADOR_L2);
    }

    /**
     * domicilio comprador, linea 2
     * @param DomicilioComprador_linea2 
     */
    public void setDomicilioComprador_linea2(String DomicilioComprador_linea2) {
        params.put(InParam.AFNC__DOMIC_COMPRADOR_L2, DomicilioComprador_linea2);
    }

    /**
     * domicilio comprador, linea 3
     * @return 
     */
    public String getDomicilioComprador_linea3() {
        return params.get(InParam.AFNC__DOMIC_COMPRADOR_L3);
    }

    /**
     * domicilio comprador, linea 3
     * @param DomicilioComprador_linea3 
     */
    public void setDomicilioComprador_linea3(String DomicilioComprador_linea3) {
        params.put(InParam.AFNC__DOMIC_COMPRADOR_L3, DomicilioComprador_linea3);
    }

    /**
     * remitos relacionados, linea 1
     * @return 
     */
    public String getRemitosRelacionados_linea1() {
        return params.get(InParam.AFNC__REMITOS_RELAC_L1);
    }

    /**
     * remitos relacionados, linea 1
     * @param RemitosRelacionados_linea1 
     */
    public void setRemitosRelacionados_linea1(String RemitosRelacionados_linea1) {
        params.put(InParam.AFNC__REMITOS_RELAC_L1, RemitosRelacionados_linea1);
    }

    /**
     * remitos relacionados, linea 2
     * @return 
     */
    public String getRemitosRelacionados_linea2() {
        return params.get(InParam.AFNC__REMITOS_RELAC_L2);
    }

    /**
     * remitos relacionados, linea 2
     * @param RemitosRelacionados_linea2 
     */
    public void setRemitosRelacionados_linea2(String RemitosRelacionados_linea2) {
        params.put(InParam.AFNC__REMITOS_RELAC_L2, RemitosRelacionados_linea2);
    }

    /**
     * es bien de uso
     * @return 
     */
    public Boolean getBienDeUso() {
        return params.get(InParam.AFNC__LEYENDA_BIEN_USO).equals("B");
    }

    /**
     * es bien de uso
     * @param bienDeUso 
     */
    public void setBienDeUso(Boolean bienDeUso) {
        params.put(InParam.AFNC__LEYENDA_BIEN_USO, bienDeUso ? "B" : "N");
    }

    /**
     * copias
     * @return 
     */
    public Integer getCopias() {
        return Integer.parseInt(params.get(InParam.AFNC__CANT_COPIAS));
    }

    /**
     * copias
     * @param copias 
     */
    public void setCopias(Integer copias) {
        params.put(InParam.AFNC__CANT_COPIAS, copias.toString());
    }

    /**
     * densidad de impresion
     * @return 
     */
    public DensidadImpresion getCpi() {
        return DensidadImpresion.parseLetra(params.get(InParam.AFNC__DENSIDAD_LETRA));
    }

    /**
     * densidad de impresion
     * @param cpi 
     */
    public void setCpi(DensidadImpresion cpi) {
        params.put(InParam.AFNC__DENSIDAD_LETRA, cpi.getCpi());
    }

    /**
     * Formato de datos
     * @return 
     */
    public FormatoDatos getFormatoDatos() {
        return FormatoDatos.parseLetra(params.get(InParam.AFNC__FORMATO_ALMAC_DAT));
    }

    /**
     * Formato de datos
     * @param formatoDatos 
     */
    public void setFormatoDatos(FormatoDatos formatoDatos) {
        params.put(InParam.AFNC__FORMATO_ALMAC_DAT, formatoDatos.getLetra());
    }

    /**
     * Letra del documento fiscal
     * @return 
     */
    public LetraDocumento getLetra() {
        return LetraDocumento.parseLetra(params.get(InParam.AFNC__LETRA_DOCUMENTO));
    }

    /**
     * Letra del documento fiscal
     * @param letra 
     */
    public void setLetra(LetraDocumento letra) {
        params.put(InParam.AFNC__LETRA_DOCUMENTO, letra.getLetra());
    }

    /**
     * Responsabilidad ante el afip del comprador
     * @return 
     */
    public ResponsabilidadAFIP getRespComprador() {
        return ResponsabilidadAFIP.parseLetra(params.get(InParam.AFNC__RESP_AFIP_COMPRADOR));
    }

    /**
     * Responsabilidad ante el afip del comprador
     * @param respComprador 
     * @throws com.gt.ifepson.IFException 
     */
    public void setRespComprador(ResponsabilidadAFIP respComprador) throws IFException {
        if(!respComprador.isPuedeComprador()) {
            throw new IFException("Responsabilidad afip incorracta para comprador");
        }
        
        params.put(InParam.AFNC__RESP_AFIP_COMPRADOR, respComprador.getLetra());
    }

    /**
     * Responsabilidad ante el afip del vendedor
     * @return 
     */
    public ResponsabilidadAFIP getRespVendedor() {
        return ResponsabilidadAFIP.parseLetra(params.get(InParam.AFNC__RESP_AFIP_VENDEDOR));
    }

    /**
     * Responsabilidad ante el afip del vendedor
     * @param respVendedor 
     * @throws com.gt.ifepson.IFException 
     */
    public void setRespVendedor(ResponsabilidadAFIP respVendedor) throws IFException {
        if(!respVendedor.isPuedeVendedor()) {
            throw new IFException("Responsabilidad afip incorracta para vendedor");
        }
        
        params.put(InParam.AFNC__RESP_AFIP_VENDEDOR, respVendedor.getLetra());
    }

    /**
     * Tipo del comprobante
     * @return 
     */
    public TipoComprobante getTipoComprobante() {
        return TipoComprobante.parseLetra(params.get(InParam.AFNC__TIPO_DOC_FISCAL));
    }

    /**
     * Tipo del comprobante
     * @param tipoComprobante 
     */
    public void setTipoComprobante(TipoComprobante tipoComprobante) {
        params.put(InParam.AFNC__TIPO_DOC_FISCAL, tipoComprobante.getLetra());
    }

    /**
     * Tipo del dni del comprador
     * @return 
     */
    public TipoDocComprador getTipoDNIComprador() {
        return TipoDocComprador.parseString(params.get(InParam.AFNC__TIPO_DOC_COMPRADOR));
    }

    /**
     * tipo del dni del comprador
     * @param tipoDNIComprador 
     */
    public void setTipoDNIComprador(TipoDocComprador tipoDNIComprador) {
        params.put(InParam.AFNC__TIPO_DOC_COMPRADOR, tipoDNIComprador.getTipo());
    }

    /**
     * tipo de formulario
     * @return 
     */
    public TipoFormulario getTipoFormulario() {
        return TipoFormulario.parseLetra(params.get(InParam.AFNC__TIPO_FORMULARIO));
    }

    /**
     * tipo del formulario
     * @param tipoFormulario 
     */
    public void setTipoFormulario(TipoFormulario tipoFormulario) {
        params.put(InParam.AFNC__TIPO_FORMULARIO, tipoFormulario.getLetra());
    }

    /**
     * tipo de salida
     * @return 
     */
    public TipoSalida getTipoSalida() {
        return TipoSalida.parseLetra(params.get(InParam.AFNC__TIPO_SALIDA_IMPRESA));
    }

    /**
     * tipo de salida
     * @param tipoSalida 
     */
    public void setTipoSalida(TipoSalida tipoSalida) {
        params.put(InParam.AFNC__TIPO_SALIDA_IMPRESA, tipoSalida.getLetra());
    }
    
}
