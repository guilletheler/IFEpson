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
public class SolEstado extends IfCommand {

    /**
     * Tipo de solicitud
     * @return 
     */
    public TipoSolicitud getTipoSolicitud() {
        return TipoSolicitud.parse(params.get(InParam.SE__TIPO_SOLICITUD));
    }

    /**
     * Tipo de solicitud
     * @param tipoSolicitud 
     */
    public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
        params.put(InParam.SE__TIPO_SOLICITUD, tipoSolicitud.getTipo());
    }

    /**
     * Tipo de solicitud como string
     * @param tipoSolicitud 
     */
    public void setTipoSolicitud(String tipoSolicitud) {
        setTipoSolicitud(TipoSolicitud.parse(tipoSolicitud));
    }

    /**
     * Constructor
     */
    public SolEstado() {
        this.commandId = 0x2A;
        this.name = "SolicitudEstado";
        this.descripcionComando = "Este comando se utiliza para conocer el estado del Impresor Fiscal. Puede ser enviado en cualquier instante ya que no realiza impresión alguna. Es conveniente llamar esta función antes de iniciar un Documento Fiscal, pero cuando se realiza la generación de documentos, los primeros dos campos de datos poseen la información necesaria para saber si el comando enviado ha sido ejecutado satisfactoriamente.";

        this.params.put(InParam.SE__TIPO_SOLICITUD, TipoSolicitud.CaracteristicasControlador.getTipo());
    }

    @Override
    public boolean interpretaRespuesta(byte[] resp) {

        String vals[] = IfCommand.bytesToStringArray(resp, (char) 0x02, (char) 0x1c, (char) 0x03);
/*
        Logger.getLogger(cierreXZ.class).log(Level.DEBUG, "Se parsearon " + vals.length + " parametros");
        
        for (int i = 0; i < vals.length; i++) {
            Logger.getLogger(cierreXZ.class).log(Level.DEBUG, "[" + i + "] " + vals[i]);
        }
*/
        try {
            if (this.params.get(InParam.SE__TIPO_SOLICITUD).equalsIgnoreCase("N")) {
                /*
                respuesta.put(OutParam.SE__N_ULTIM_TIQ_O_FAC_B_C, GeneralFunc.extractVarStrFromArray(resp, 14, (char) 0x1C));
                respuesta.put(OutParam.SE__N_FECH_PRIM_COMP, GeneralFunc.extractVarStrFromArray(resp, 23, (char) 0x1C));
                respuesta.put(OutParam.SE__N_HORA_PRIM_COMP, GeneralFunc.extractVarStrFromArray(resp, 30, (char) 0x1C));
                respuesta.put(OutParam.SE__N_ULTIMA_Z, GeneralFunc.extractVarStrFromArray(resp, 37, (char) 0x1C));
                respuesta.put(OutParam.SE__N_DATO_AUDIT_PARC, GeneralFunc.extractVarStrFromArray(resp, 43, (char) 0x1C));
                respuesta.put(OutParam.SE__N_DATO_AUDIT_TOTAL, GeneralFunc.extractVarStrFromArray(resp, 52, (char) 0x1C));
                respuesta.put(OutParam.SE__N_ID_TXT_AUDIT, GeneralFunc.extractVarStrFromArray(resp, 61, (char) 0x1C));
                respuesta.put(OutParam.SE__N_TXT_AUDIT, GeneralFunc.extractVarStrFromArray(resp, 72, (char) 0x1C, (char) 0x03));
                 */
                if (vals.length < 10) {
                    // ERROR!!
                    respuesta.put(OutParam.OTROS_ERRORES, "Error al interpretar respuesta en solEstado N");
                    return false;
                }

                respuesta.put(OutParam.SE__N_ULTIM_TIQ_O_FAC_B_C, vals[3]);
                respuesta.put(OutParam.SE__N_FECH_PRIM_COMP, vals[4]);
                respuesta.put(OutParam.SE__N_HORA_PRIM_COMP, vals[5]);
                respuesta.put(OutParam.SE__N_ULTIMA_Z, vals[6]);
                respuesta.put(OutParam.SE__N_DATO_AUDIT_PARC, vals[7]);
                respuesta.put(OutParam.SE__N_DATO_AUDIT_TOTAL, vals[8]);
                respuesta.put(OutParam.SE__N_ID_TXT_AUDIT, vals[9]);
                respuesta.put(OutParam.SE__N_TXT_AUDIT, vals[10]);

            } else if (this.params.get(InParam.SE__TIPO_SOLICITUD).equalsIgnoreCase("P")) {

                /*
                respuesta.put(OutParam.SE__P_CANT_COL_10CPI, GeneralFunc.extractVarStrFromArray(resp, 14, (char) 0x1C));
                respuesta.put(OutParam.SE__P_CANT_COL_12CPI, GeneralFunc.extractVarStrFromArray(resp, 18, (char) 0x1C));
                respuesta.put(OutParam.SE__P_CANT_COL_17CPI, GeneralFunc.extractVarStrFromArray(resp, 22, (char) 0x1C));
                respuesta.put(OutParam.SE__P_CANT_COL_TIQUE, GeneralFunc.extractVarStrFromArray(resp, 26, (char) 0x1C));
                respuesta.put(OutParam.SE__P_CANT_LIN_VALID, GeneralFunc.extractVarStrFromArray(resp, 30, (char) 0x1C, (char) 0x03));
                respuesta.put(OutParam.SE__P_PUEDE_TIQUET, resp[34] == 'S' ? "1" : "0");
                respuesta.put(OutParam.SE__P_PUEDE_TIQUEFACT, resp[36] == 'S' ? "1" : "0");
                respuesta.put(OutParam.SE__P_PUEDE_FACTURA, resp[38] == 'S' ? "1" : "0");
                respuesta.put(OutParam.SE__P_DIGIT_CENT_Z, GeneralFunc.extractVarStrFromArray(resp, 40, (char) 0x1C));
                respuesta.put(OutParam.SE__P_EST_PRINC_SELEC, GeneralFunc.extractVarStrFromArray(resp, 42, (char) 0x1C));
                respuesta.put(OutParam.SE__P_MODELO_IMPRESORA, GeneralFunc.extractVarStrFromArray(resp, 45, (char) 0x1C, (char) 0x03));
                 */

                if (vals.length < 13) {
                    // ERROR!!
                    respuesta.put(OutParam.OTROS_ERRORES, "Error al interpretar respuesta en solEstado P");
                    return false;
                }


                respuesta.put(OutParam.SE__P_CANT_COL_10CPI, vals[3]);
                respuesta.put(OutParam.SE__P_CANT_COL_12CPI, vals[4]);
                respuesta.put(OutParam.SE__P_CANT_COL_17CPI, vals[5]);
                respuesta.put(OutParam.SE__P_CANT_COL_TIQUE, vals[6]);
                respuesta.put(OutParam.SE__P_CANT_LIN_VALID, vals[7]);
                respuesta.put(OutParam.SE__P_PUEDE_TIQUET, vals[8].equals("S") ? "1" : "0");
                respuesta.put(OutParam.SE__P_PUEDE_TIQUEFACT, vals[9].equals("S") ? "1" : "0");
                respuesta.put(OutParam.SE__P_PUEDE_FACTURA, vals[8].equals("10") ? "1" : "0");
                respuesta.put(OutParam.SE__P_DIGIT_CENT_Z, vals[11]);
                respuesta.put(OutParam.SE__P_EST_PRINC_SELEC, vals[12]);
                respuesta.put(OutParam.SE__P_MODELO_IMPRESORA, vals[13]);

            } else if (this.params.get(InParam.SE__TIPO_SOLICITUD).equalsIgnoreCase("C")) {
                /*
                respuesta.put(OutParam.SE__C_CUIT, GeneralFunc.extractVarStrFromArray(resp, 14, (char) 0x1C));
                respuesta.put(OutParam.SE__C_PTO_VTA, GeneralFunc.extractVarStrFromArray(resp, 26, (char) 0x1C));
                respuesta.put(OutParam.SE__C_TIPO_RESP, GeneralFunc.extractVarStrFromArray(resp, 31, (char) 0x1C));
                respuesta.put(OutParam.SE__C_IVA_STD, GeneralFunc.extractVarStrFromArray(resp, 33, (char) 0x1C));
                respuesta.put(OutParam.SE__C_MONTO_MAX_TIQUEFAC, GeneralFunc.extractVarStrFromArray(resp, 38, (char) 0x1C));
                respuesta.put(OutParam.SE__C_RAZON_SOC_COMPRADOR, GeneralFunc.extractVarStrFromArray(resp, 58, (char) 0x1C, (char) 0x03));
                 */

                if (vals.length < 8) {
                    // ERROR!!
                    respuesta.put(OutParam.OTROS_ERRORES, "Error al interpretar respuesta en solEstado C");
                    return false;
                }


                respuesta.put(OutParam.SE__C_CUIT, vals[3]);
                respuesta.put(OutParam.SE__C_PTO_VTA, vals[4]);
                respuesta.put(OutParam.SE__C_TIPO_RESP, vals[5]);
                respuesta.put(OutParam.SE__C_IVA_STD, vals[6]);
                respuesta.put(OutParam.SE__C_MONTO_MAX_TIQUEFAC, vals[7]);
                respuesta.put(OutParam.SE__C_RAZON_SOC_COMPRADOR, vals[8]);

            } else if (this.params.get(InParam.SE__TIPO_SOLICITUD).equalsIgnoreCase("A")) {
                /*
                respuesta.put(OutParam.SE__A_ULT_Z, GeneralFunc.extractVarStrFromArray(resp, 14, (char) 0x1C));
                respuesta.put(OutParam.SE__A_ULT_TIQUFAC_B_C, GeneralFunc.extractVarStrFromArray(resp, 20, (char) 0x1C));
                respuesta.put(OutParam.SE__A_ULT_TIQFAC_B_C_IMPR, GeneralFunc.extractVarStrFromArray(resp, 30, (char) 0x1C));
                respuesta.put(OutParam.SE__A_ULT_TIQFAC_A, GeneralFunc.extractVarStrFromArray(resp, 38, (char) 0x1C));
                respuesta.put(OutParam.SE__A_ULT_TIQFAC_A_IMPR, GeneralFunc.extractVarStrFromArray(resp, 47, (char) 0x1C));
                respuesta.put(OutParam.SE__A_ULT_NO_FISC, GeneralFunc.extractVarStrFromArray(resp, 56, (char) 0x1C));
                respuesta.put(OutParam.SE__A_ULT_NO_FISC_HOMO, GeneralFunc.extractVarStrFromArray(resp, 62, (char) 0x1C));
                respuesta.put(OutParam.SE__A_ULT_NRO_REF_DOC_NO_FISC, GeneralFunc.extractVarStrFromArray(resp, 68, (char) 0x1C));
                respuesta.put(OutParam.SE__A_ULT_TIQFAC_NOTA_CRED_A, GeneralFunc.extractVarStrFromArray(resp, 77, (char) 0x1C));
                respuesta.put(OutParam.SE__A_ULT_TIQFAC_NOTA_CRED_B_C, GeneralFunc.extractVarStrFromArray(resp, 86, (char) 0x1C));
                respuesta.put(OutParam.SE__A_ULT_REMI, GeneralFunc.extractVarStrFromArray(resp, 95, (char) 0x1C, (char) 0x03));
                 */

                if (vals.length < 13) {
                    // ERROR!!
                    respuesta.put(OutParam.OTROS_ERRORES, "Error al interpretar respuesta en solEstado A");
                    return false;
                }

                respuesta.put(OutParam.SE__A_ULT_Z, vals[3]);
                respuesta.put(OutParam.SE__A_ULT_TIQUFAC_B_C, vals[4]);
                respuesta.put(OutParam.SE__A_ULT_TIQFAC_B_C_IMPR, vals[5]);
                respuesta.put(OutParam.SE__A_ULT_TIQFAC_A, vals[6]);
                respuesta.put(OutParam.SE__A_ULT_TIQFAC_A_IMPR, vals[7]);
                respuesta.put(OutParam.SE__A_ULT_NO_FISC, vals[8]);
                respuesta.put(OutParam.SE__A_ULT_NO_FISC_HOMO, vals[9]);
                respuesta.put(OutParam.SE__A_ULT_NRO_REF_DOC_NO_FISC, vals[10]);
                respuesta.put(OutParam.SE__A_ULT_TIQFAC_NOTA_CRED_A, vals[11]);
                respuesta.put(OutParam.SE__A_ULT_TIQFAC_NOTA_CRED_B_C, vals[12]);
                respuesta.put(OutParam.SE__A_ULT_REMI, vals[13]);

            } else if (this.params.get(InParam.SE__TIPO_SOLICITUD).equalsIgnoreCase("D")) {
                /*
                String tmpStr = GeneralFunc.extractConstStrFromArray(resp, 14, 1);
                
                respuesta.put(OutParam.SE__D_TIPO_DOC, tmpStr);
                
                if (tmpStr.compareTo("N") == 0) {
                respuesta.put(OutParam.SE__D_LETRA_DOC, tmpStr);
                } else {
                respuesta.put(OutParam.SE__D_LETRA_DOC, GeneralFunc.extractConstStrFromArray(resp, 16, 1));
                }
                 */

                if (vals.length < 3) {
                    // ERROR!!
                    respuesta.put(OutParam.OTROS_ERRORES, "Error al interpretar respuesta en solEstado N");
                    return false;
                }


                respuesta.put(OutParam.SE__D_TIPO_DOC, vals[3]);

                if (vals[3].compareTo("N") == 0) {
                    respuesta.put(OutParam.SE__D_LETRA_DOC, vals[3]);
                } else {
                    respuesta.put(OutParam.SE__D_LETRA_DOC, vals[4]);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(SolEstado.class).log(Level.ERROR, "", e);
            return false;
        }

        //respuesta.add(new String(resp, 0, resp.length));

        return true;
    }

    /**
     * Tipo de solicitud
     */
    public static enum TipoSolicitud {

        /**
         * Información normal
         */
        InfoNormal("N"),
        /**
         * Características del controlador
         */
        CaracteristicasControlador("P"),
        /**
         * Información contribuyente
         */
        InfoContribuyente("C"),
        /**
         * Información contadores
         */
        InfoContadores("A"),
        /**
         * Información documento actual
         */
        InfoCurDoc("D"),
        /**
         * Información preferencias
         */
        InfoPreferencias("S");
        
        String letra = "N";

        TipoSolicitud(String letra) {
            this.letra = letra;
        }

        /**
         * Abreviatura
         * @return 
         */
        public String getTipo() {
            return letra;
        }

        /**
         * Abreviatura a TipoSolicitud
         * @param letter
         * @return 
         */
        public static TipoSolicitud parse(String letter) {
            if (letter.equalsIgnoreCase("p") || letter.equalsIgnoreCase("CaracteristicasControlador")) {
                return TipoSolicitud.CaracteristicasControlador;
            }

            if (letter.equalsIgnoreCase("c") || letter.equalsIgnoreCase("InfoContribuyente")) {
                return TipoSolicitud.InfoContribuyente;
            }

            if (letter.equalsIgnoreCase("a") || letter.equalsIgnoreCase("InfoContadores")) {
                return TipoSolicitud.InfoContadores;
            }

            if (letter.equalsIgnoreCase("d") || letter.equalsIgnoreCase("InfoCurDoc")) {
                return TipoSolicitud.InfoCurDoc;
            }

            if (letter.equalsIgnoreCase("s") || letter.equalsIgnoreCase("InfoPreferencias")) {
                return TipoSolicitud.InfoPreferencias;
            }

            return TipoSolicitud.InfoNormal;
        }
    }
}
