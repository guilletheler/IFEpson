/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.capa_fisica.serialPort;

import com.gt.ifepson.fiscaldoc.FiscalDocument;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author guillermot
 */
public class SerialPortConnector {

    /**
     * Configuración del puerto serie
     */
    protected PortConfig portConfig;

    /**
     * Constructor
     * @param portConfig 
     */
    public SerialPortConnector(PortConfig portConfig) {
        this.portConfig = portConfig;
    }

    /**
     * Obtiene el puerto serie abierto y configurado, es equivalente a<br/>
     * SerialPortConnector spc = new SerialPortConnector();<br/>
     * spc.setPortConfig(portConfig);<br/>
     * spc.getSerialPort();
     * @param portConfig configuración a utilizar
     * @return 
     */
    public SerialPort getSerialPort(PortConfig portConfig) {

        this.setPortConfig(portConfig);
        return this.getSerialPort();
    }

    /**
     * Obtiene el puerto serie abierto y configurado
     * @return 
     */
    public SerialPort getSerialPort() {
        SerialPort port;

        Logger.getLogger(FiscalDocument.class).log(
                Level.DEBUG,
                "Abriendo el puerto "
                + this.getPortConfig().getPortName());

        port = new SerialPort(this.getPortConfig().getPortName());
        try {
            port.openPort();
            port.setParams(this.getPortConfig().getBaudRate(), this.getPortConfig().getDataBits(), this.getPortConfig().getStopBits(), this.getPortConfig().getParity(), true, true);
            //port.setDTR(true);

        } catch (SerialPortException ex) {
            Logger.getLogger(SerialPortConnector.class).log(Level.FATAL, "Error abriendo puerto", ex);
        }

        return port;
    }

    /**
     * Configuración del puerto
     * @return 
     */
    public PortConfig getPortConfig() {
        return portConfig;
    }

    /**
     * Configuración del puerto
     * @param portConfig 
     */
    public void setPortConfig(PortConfig portConfig) {
        this.portConfig = portConfig;
    }

}
