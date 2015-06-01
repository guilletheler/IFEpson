/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;
import java.util.Calendar;

/**
 *
 * @author guillermot
 */
public class SetFechaHora extends IfCommand {

    /**
     * fecha
     * @param fecha 
     */
    public void setFecha(String fecha) {
        params.put(InParam.EFH__FECHA, fecha);
    }

    /**
     * fecha
     * @return 
     */
    public String getFecha() {
        return params.get(InParam.EFH__FECHA);
    }

    /**
     * hora
     * @param hora 
     */
    public void setHora(String hora) {
        params.put(InParam.EFH__HORA, hora);
    }

    /**
     * hora
     * @return 
     */
    public String getHora() {
        return params.get(InParam.EFH__HORA);
    }

    /**
     * fecha/hora como Calendar
     * @return 
     */
    public Calendar getFechaHora() {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, Integer.parseInt(getFecha().substring(0, 1)) + 2000);
        c.set(Calendar.MONTH, Integer.parseInt(getFecha().substring(2, 3)) - 1);
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(getFecha().substring(4)));

        c.set(Calendar.HOUR, Integer.parseInt(getHora().substring(0, 1)));
        c.set(Calendar.MINUTE, Integer.parseInt(getHora().substring(2, 3)));
        c.set(Calendar.SECOND, Integer.parseInt(getHora().substring(4)));

        return c;
    }

    /**
     * fecha/hora como Calendar
     * @param c 
     */
    public void setFechaHora(final Calendar c) {
        params.put(InParam.EFH__FECHA, String.format("%1$ty%1$tM%1$td", c));
        params.put(InParam.EFH__HORA, String.format("%1$Hy%1$tm%1$ts", c));
    }

    /**
     * Constructor
     */
    public SetFechaHora() {
        this.commandId = 0x58;
        this.name = "setFechaHora";
        this.descripcionComando = "Seteo de fecha y hora";

        this.params.put(InParam.EFH__FECHA, String.format("%1$ty%1$tM%1$td", Calendar.getInstance()));
        this.params.put(InParam.EFH__HORA, String.format("%1$tH%1$tm%1$ts",  Calendar.getInstance()));
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        return true;
    }
}
