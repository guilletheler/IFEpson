/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.commands.enums.TipoRepoMem;
import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;
import java.util.Calendar;

/**
 *
 * @author Administrador
 */
public final class RepoMemFiscFecha extends IfCommand {

    /**
     * fecha inicial como string
     * @param fecha 
     */
    public void setFechaIni(String fecha) {
        params.put(InParam.RF__FECHA_INICIO, fecha);
    }

    /**
     * Fecha inicial como string
     * @return 
     */
    public String getFechaIni() {
        return params.get(InParam.RF__FECHA_INICIO);
    }

    /**
     * fecha inicial como Calendar
     * @return 
     */
    public Calendar getCalendarIni() {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(getFechaIni().substring(0, 1)) + 2000);
        c.set(Calendar.MONTH, Integer.parseInt(getFechaIni().substring(2, 3)) - 1);
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(getFechaIni().substring(4)));
        return c;
    }

    /**
     * Seteo de fecha inicial por objeto Calendar
     * @param c 
     */
    public void setCalendarIni(Calendar c) {
        params.put(InParam.RF__FECHA_INICIO, String.format("%1$ty%1$tM%1$td", c));
    }

    /**
     * fecha final como string
     * @param fecha 
     */
    public void setFechaFin(String fecha) {
        params.put(InParam.RF__FECHA_FIN, fecha);
    }

    /**
     * Fecha final como string
     * @return 
     */
    public String getFechaFin() {
        return params.get(InParam.RF__FECHA_FIN);
    }

    /**
     * fecha final como Calendar
     * @return 
     */
    public Calendar getCalendarFin() {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(getFechaFin().substring(0, 1)) + 2000);
        c.set(Calendar.MONTH, Integer.parseInt(getFechaFin().substring(2, 3)) - 1);
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(getFechaFin().substring(4)));
        return c;
    }

    /**
     * fecha final como Calendar
     * @param c 
     */
    public void setCalendarFin(Calendar c) {
        params.put(InParam.RF__FECHA_FIN, String.format("%1$ty%1$tM%1$td", c));
    }

    /**
     * Tipo de reporte de memoria
     * @return 
     */
    public TipoRepoMem getTipo() {
        return TipoRepoMem.parseLetra(params.get(InParam.RF__TIPO_REPORTE));
    }

    /**
     * Tipo de reporte de memoria
     * @param tipo 
     */
    public void setTipo(TipoRepoMem tipo) {
        params.put(InParam.RF__TIPO_REPORTE, tipo.getLetra());
    }

    /**
     * Constructor
     */
    public RepoMemFiscFecha() {
        this.commandId = 58; //0x3A;
        this.name = "repoMemFiscFecha";
        this.descripcionComando = "Este comando imprime un reporte de Cierres Diarios en forma selectiva por un rango de fechas. Este comando usa tiempo extendido para su finalización. Además brinda la opción de producir sólo totales, o totales y detalles de Cierres Diarios.";

        //this.params.put(InParam.RF__FECHA_INICIO, getStrFechaInicio());
        setCalendarIni(Calendar.getInstance());
        //this.params.put(InParam.RF__FECHA_FIN, getStrFechaFin());
        setCalendarFin(Calendar.getInstance());
        this.params.put(InParam.RF__TIPO_REPORTE, TipoRepoMem.InfoAuditDetalle.getLetra());
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }

    
}
