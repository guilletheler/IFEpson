/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.commands.enums.CalificadorPagoCancelDescReca;
import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;
import com.gt.ifepson.capa_fisica.OutParam;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public class PagoCancelDescRecaTique extends IfCommand {

    
    /**
     * Calificador
     * @return 
     */
    public CalificadorPagoCancelDescReca getCalificador() {
        return CalificadorPagoCancelDescReca.parseString(params.get(InParam.PDRC__CALIFICADOR));
    }

    /**
     * calificador
     * @param calificador 
     */
    public void setCalificador(CalificadorPagoCancelDescReca calificador) {
        params.put(InParam.PDRC__CALIFICADOR, calificador.getLetra());
    }

    /**
     * descripcion en tiquet
     * @return 
     */
    public String getDescripcionEnTique() {
        return params.get(InParam.PDRC__DESCRIPCION);
    }

    /**
     * descripcion en tiquet
     * @param descripcionEnTique 
     */
    public void setDescripcionEnTique(String descripcionEnTique) {
        this.params.put(InParam.PDRC__DESCRIPCION, IfCommand.padRight(descripcionEnTique, 26, ' '));
    }

    /**
     * monto
     * @return 
     */
    public Double getMonto() {
        return Double.parseDouble(params.get(InParam.PDRC__MONTO)) / 100;
    }

    /**
     * monto
     * @param monto 
     */
    public void setMonto(Double monto) {
        this.params.put(InParam.PDRC__MONTO, String.format("%09d", ((Double) (monto * 100)).intValue()));
    }

    /**
     * constructor
     */
    public PagoCancelDescRecaTique() {
        this.commandId = 0x44;
        this.name = "pagoCancelarDecuentoRecargo";
        this.nombreAbreviado = "TIQUEPAGO";
        this.descripcionComando = "Se rechazará este comando si no hay un comprobante fiscal abierto. Se rechazará si los montos acumulados generan un desbordamiento del total. Se rechazará si hay un papel en las estaciones de slip o validación, si no hay papel en la estación de rollo, o si se usó la máxima cantidad de pagos permitida. Se usa este comando para imprimir información del total y del pago de la transacción. Cuando se envía un PAGO al Impresor Fiscal, se almacena y se imprime junto con el TOTAL cuando se cierra el Tique. Las transacciones de ventas deben emitir comandos de pago y pagar el total completo de la transacción antes de emitir un comando de Cierre de Comprobante Fiscal. Después de éste, no se pueden emitir nuevos comandos de impresión de ítem de línea. Una vez enviado el PAGO, sólo se aceptan comandos PAGO, CERRAR Tique o CANCELAR. Serán aceptados 5 (cinco) pagos como máximo por cada tique.";

        this.params.put(InParam.PDRC__DESCRIPCION, IfCommand.padRight(" ", 26, ' '));
        this.params.put(InParam.PDRC__MONTO, String.format("%09d", 0));
        this.params.put(InParam.PDRC__CALIFICADOR, CalificadorPagoCancelDescReca.SUMA_IMPORTE_PAGADO.getLetra());
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            if (getCalificador() != CalificadorPagoCancelDescReca.CANCELAR_COMPROBANTE) {
                respuesta.put(OutParam.PDRC__RESTO_FALTA_PAGAR, IfCommand.extractVarStrFromArray(resp, 14, (char) 0x1C, (char) 0x03));
            } else {
                respuesta.put(OutParam.PDRC__TIQUE_CANCELADO, "1");
            }
        } catch (Exception e) {
            Logger.getLogger(PagoCancelDescRecaTique.class).log(Level.ERROR, "", e);
            return false;
        }
        return true;
    }

}
