/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.commands;

import com.gt.ifepson.commands.enums.TipoPreferenciasUsuario;
import com.gt.ifepson.commands.enums.Opcion1PreferenciasUsuario;
import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.InParam;
import com.gt.ifepson.capa_fisica.OutParam;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public class LeePrefUsuario extends IfCommand {

    /**
     * Opcion 1
     * @return 
     */
    public Opcion1PreferenciasUsuario getOpcion1() {
        return Opcion1PreferenciasUsuario.parseString(params.get(InParam.LPU__OPCIONES1));
    }

    /**
     * Opcion 1
     * @param opcion1 
     */
    public void setOpcion1(Opcion1PreferenciasUsuario opcion1) {
        this.params.put(InParam.LPU__OPCIONES1, opcion1.getLetra());
    }

    /**
     * Tipo de seteo
     * @return 
     */
    public TipoPreferenciasUsuario getTipoSeteo() {
        return TipoPreferenciasUsuario.parseString(params.get(InParam.LPU__TIPO_SETEO));
    }

    /**
     * Tipo de seteo
     * @param tipoSeteo 
     */
    public void setTipoSeteo(TipoPreferenciasUsuario tipoSeteo) {
        this.params.put(InParam.LPU__TIPO_SETEO, tipoSeteo.getLetra());
    }

    /**
     * Constructor
     */
    public LeePrefUsuario() {
        this.commandId = 0x5B;
        this.name = "leerPreferenciasUsuario";
        this.descripcionComando = "Este comando se utiliza para leer de la memoria de trabajo las preferencias del usuario establecidas con el comando Seleccionar Preferencias del Usuario.";


        this.params.put(InParam.LPU__IMPRESORA, "P");
        this.params.put(InParam.LPU__TIPO_SETEO, TipoPreferenciasUsuario.PREFERENCIAS_DISPOSITIVO_IMPRESION.getLetra());
        this.params.put(InParam.LPU__OPCIONES1, Opcion1PreferenciasUsuario.TAMA_PAPEL.getLetra());

    }


    @Override
    public boolean interpretaRespuesta(byte[] resp) {
        try {
            if (getTipoSeteo() == TipoPreferenciasUsuario.PREFERENCIAS_DISPOSITIVO_IMPRESION) {
                if (resp[14] == 'R') {
                    respuesta.put(OutParam.LPU__IMPRESION_ROLLO_PAPEL, "1");
                }

                if (resp[14] == 'S') {
                    respuesta.put(OutParam.LPU__IMPRESION_HOJA_SUELTA, "1");
                }

                if (resp[15] == 'O') {
                    respuesta.put(OutParam.LPU__TIPO_DOC_NO_FISCAL, "1");
                }
            }

            if (getTipoSeteo() == TipoPreferenciasUsuario.PREFERENCIAS_DISPOSITIVO_IMPRESION && getOpcion1() == Opcion1PreferenciasUsuario.TAMA_PAPEL) {
                if (resp[14] == 'U') {
                    respuesta.put(OutParam.LPU__TAMA_DEFINIDO_POR_USR, "1");
                }
            }

            if (getTipoSeteo() == TipoPreferenciasUsuario.PREFERENCIAS_COMPROBANTES_FISCALES && (getOpcion1() == Opcion1PreferenciasUsuario.PREF_IMPRIMIR_PREC_X_CANTIDAD || getOpcion1() == Opcion1PreferenciasUsuario.PREF_IMPRIMIR_LEYENDA)) {
                if (resp[14] == 'N') {
                    respuesta.put(OutParam.LPU__PREF_SOL_NO_SETEADA, "1");
                }

                if (resp[14] == 'S') {
                    respuesta.put(OutParam.LPU__PREF_SOL_SETEADA, "1");
                }

                respuesta.put(OutParam.LPU__CANTIDAD_COLUMNAS, ((Integer) (int) resp[15]).toString());
                respuesta.put(OutParam.LPU__CANTIDAD_FILAS, ((Integer) (int) resp[16]).toString());

            }
        } catch (Exception e) {
            Logger.getLogger(LeePrefUsuario.class).log(Level.ERROR, "", e);
            return false;
        }

        return true;
    }

    

}
