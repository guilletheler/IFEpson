/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.commands.enums.TipoRepoMem;
import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;

/**
 *
 * @author Administrador
 */
public class RepoMemFiscZ extends IfCommand {

    /**
     * tipo de reporte
     *
     * @return
     */
    public TipoRepoMem getTipo() {
        return TipoRepoMem.parseLetra(params.get(InParam.RZ__TIPO_REPORTE));
    }

    /**
     * tipo de reporte
     *
     * @param tipo
     */
    public void setTipo(TipoRepoMem tipo) {
        params.put(InParam.RZ__TIPO_REPORTE, tipo.getLetra());
    }

    /**
     * nro de z final
     *
     * @return
     */
    public Integer getzFin() {
        return Integer.parseInt(params.get(InParam.RZ__Z_FIN));
    }

    /**
     * nro de z final
     *
     * @param zFin
     */
    public void setzFin(Integer zFin) {
        params.put(InParam.RZ__Z_FIN, String.format("%04d", zFin));
    }

    /**
     * nro de z inicial
     *
     * @return
     */
    public Integer getzIni() {
        return Integer.parseInt(params.get(InParam.RZ__Z_INICIO));
    }

    /**
     * nro de z inicial
     *
     * @param zIni
     */
    public void setzIni(Integer zIni) {
        params.put(InParam.RZ__Z_INICIO, String.format("%04d", zIni));
    }

    /**
     * constructor
     */
    public RepoMemFiscZ() {
        this.commandId = 0x3B;
        this.name = "reporteMemoriaFiscalPorZ";
        this.descripcionComando = "Este comando imprime un reporte de cierres diarios en forma selectiva por un rango de números de cierre. Este comando usa tiempo extendido para la finalización. Provee la opción de producir sólo totales, o totales y detalles de Cierres Diarios.";

        this.params.put(InParam.RZ__Z_INICIO, IfCommand.padLeft("0", 4, '0'));
        this.params.put(InParam.RZ__Z_FIN, IfCommand.padLeft("0", 4, '0'));
        this.params.put(InParam.RZ__TIPO_REPORTE, TipoRepoMem.InfoAuditDetalle.getLetra());
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

}
