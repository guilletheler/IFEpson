/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson;

/**
 *
 * @author guillermot
 */
public enum IFReturnValue {

    /**
     * todo bien
     */
    OK("todo bien"),
    /**
     * recesita reintentar
     */
    NEED_RETRY("recesita reintentar"),
    /**
     * ERROR: tiempo de espera esta agotado en enviar comando
     */
    COMMAND_TIMEOUT_ERROR("ERROR: tiempo de espera esta agotado en enviar comando"),
    /**
     * ERROR: tiempo de espera esta agotado en recibir respuesta
     */
    RESPONSE_TIMEOUT_ERROR("ERROR: tiempo de espera esta agotado en recibir respuesta"),
    /**
     * ERROR: respuesta es vacia
     */
    EMPTY_RESPONSE_ERROR("ERROR: respuesta es vacia"),
    /**
     * ERROR: error desconocido
     */
    UNKNOW_ERROR("ERROR: error desconocido"),
    /**
     * ERROR: error fiscal
     */
    FISCAL_ERROR("ERROR: error fiscal"),
    /**
     * ERROR: error al interpretar respuesta
     */
    UNKNOW_RESPONSE_ERROR("ERROR: error al interpretar respuesta"),
    /**
     * ERROR: no existe el puerto serie
     */
    UNKNOW_SERIAL_PORT_ERROR("ERROR: no existe el puerto serie"),
    /**
     * ERROR: puerto serieen uso
     */
    SERIAL_PORT_IN_USE_ERROR("ERROR: puerto serieen uso"),
    /**
     * ERROR: recibí un NAK
     */
    NAK_RECIVED_ERROR("ERROR: recibí un NAK"),
    /**
     * ERROR: error de impresion
     */
    PRINTER_ERROR("ERROR: error de impresion"),
    /**
     * ERROR: secuencia fuera de rango
     */
    SERIAL_OUT_OF_RANGE_ERROR("ERROR: secuencia fuera de rango"),
    /**
     * ERROR: Operacion no soportada por el puerto serie
     */
    SERIAL_PORT_UNSOPORTED_OP_ERROR("ERROR: Operacion no soportada por el puerto serie");
    
    String description = "";

    IFReturnValue(String description) {
        this.description = description;
    }
    
    /**
     * descripcion
     * @return 
     */
    public String getDescription() {
        return description;
    }
    
}
