/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gt.ifepson.commands;

import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;
import com.gt.ifepson.capa_fisica.OutParam;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public class CerrarTique extends IfCommand {

    /**
     * Corte de total
     * @return 
     */
    public boolean isCorteTotal() {
        return params.get(InParam.CT__CORTE_TOTAL).equals("T");
    }

    /**
     * Corte de total
     * @param corteTotal 
     */
    public void setCorteTotal(boolean corteTotal) {
        params.put(InParam.CT__CORTE_TOTAL, corteTotal ? "T" : "P");
    }

    /**
     * Constructor
     */
    public CerrarTique() {
        this.commandId = 0x45;
        this.name = "cerrarComprobanteTiqueFiscal";
        this.nombreAbreviado = "TIQUECIERRA";
        this.descripcionComando = "Se rechazará el comando si no hay un tique fiscal abierto. Se lo rechazará si no se completó alguna transacción de Venta con total mayor que cero ó si los montos acumulativos originan un desbordamiento del total. Se lo rechazará si hay formularios en las estaciones de slip ó validación ó si se hubiera agotado el papel de rollo. Este comando se usa para cerrar el comprobante fiscal, acumular totales en Totales Diarios en la Memoria de Trabajo, imprimir el Importe Total del Tique, el importe de los pagos, el vuelto, el logo fiscal y cortar el comprobante fiscal. ";

        this.params.put(InParam.CT__CORTE_TOTAL, "T");
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            respuesta.put(OutParam.CT__NRO_TIQUE, IfCommand.extractVarStrFromArray(resp, 14, (char) 0x1C, (char) 0x03));
        } catch (Exception e) {
            Logger.getLogger(CerrarTique.class).log(Level.ERROR, "", e);
            return false;
        }
        return true;
    }

}
