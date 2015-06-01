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
public class PagoCancelDescRecaFNC extends IfCommand {

    /**
     * CalificadorPagoCancelDescReca
     *
     * @return
     */
    public CalificadorPagoCancelDescReca getCalificador() {
        return CalificadorPagoCancelDescReca.parseString(this.params.get(InParam.PDRTCFNC__CALIFICADOR));
    }

    /**
     * CalificadorPagoCancelDescReca
     *
     * @param calificador
     */
    public void setCalificador(CalificadorPagoCancelDescReca calificador) {
        this.params.put(InParam.PDRTCFNC__CALIFICADOR, calificador.getLetra());
    }

    /**
     * Descripción en tiquet
     *
     * @return
     */
    public String getDescripcionEnTique() {
        return params.get(InParam.PDRTCFNC__DESCRIPCION);
    }

    /**
     * Descripción en tiquet
     *
     * @param descripcionEnTique
     */
    public void setDescripcionEnTique(String descripcionEnTique) {
        params.put(InParam.PDRTCFNC__DESCRIPCION, descripcionEnTique);
    }

    /**
     * MOnto
     *
     * @return
     */
    public Double getMonto() {
        return Double.parseDouble(this.params.get(InParam.PDRTCFNC__MONTO)) / 100;
    }

    /**
     * monto
     *
     * @param monto
     */
    public void setMonto(Double monto) {
        this.params.put(InParam.PDRTCFNC__MONTO, String.format("%09d", ((Double) (monto * 100)).intValue()));
    }

    /**
     * Constructor
     */
    public PagoCancelDescRecaFNC() {
        this.commandId = 0x64;
        this.name = "pagoCancelarDecuentoFNC";
        this.descripcionComando = "Se rechazará este comando si no hay un comprobante fiscal abierto. Se rechazará si los montos acumulados generan un desbordamiento de total. En Impresoras de Tique y Tique-Factura/TNC se rechazará si hay un papel en las estaciones de slip o validación, si no hay papel en la estación de rollo, o si se usó la máxima cantidad de pagos permitida. Se usa este comando para imprimir información del total del pago y vuelto de la transacción. Cuando se envía un PAGO al Impresor Fiscal, se almacena y se imprimen junto con el TOTAL cuando se cierra la Factura / Nota de Crédito / TF / TNC. Después de este comando, no se pueden emitir nuevos comandos de impresión ítem de línea. Una vez enviado un PAGO, sólo se aceptan los comandos Pago, Cerrar Factura / Nota de Crédito / TF / TNC o CANCELAR. Sólo serán aceptados 5 (cinco) pagos en total por cada Factura / Nota de Crédito / TF / TNC.";
        this.nombreAbreviado = "FACTPAGO";

        this.params.put(InParam.PDRTCFNC__DESCRIPCION, String.format("%0$-26s", ""));
        this.params.put(InParam.PDRTCFNC__MONTO, String.format("%011d", 0));
        this.params.put(InParam.PDRTCFNC__CALIFICADOR, CalificadorPagoCancelDescReca.SUMA_IMPORTE_PAGADO.getLetra());
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            if (getCalificador() != CalificadorPagoCancelDescReca.CANCELAR_COMPROBANTE) {
                respuesta.put(OutParam.PDRTCFNC__RESTO_FALTA_PAGAR, IfCommand.extractVarStrFromArray(resp, 14, (char) 0x1C, (char) 0x03));
            }
        } catch (Exception e) {
            Logger.getLogger(PagoCancelDescRecaFNC.class).log(Level.ERROR, "", e);
            return false;
        }
        return true;
    }
}
