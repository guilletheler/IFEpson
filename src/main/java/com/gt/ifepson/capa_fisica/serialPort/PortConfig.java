/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.capa_fisica.serialPort;

/**
 * Configuracion del puerto serie
 *
 * @author guillermot
 */
public class PortConfig {

    /**
     * Serial Port Baudrate
     */
    protected Integer baudRate = 9600;

    /**
     * Serial Port DataBits
     */
    protected Integer dataBits = 7;
    
    /**
     * Serial Port StopBits
     */
    protected Integer stopBits = 1;
    
    /**
     * Serial Port Parity
     */
    protected Integer parity = 0;
    
    private boolean setted = false;
    String portName = "/dev/ttyS0";

    /**
     * Constructor
     */
    public PortConfig() {

    }

    /**
     * Constructor
     * @param portName
     * @param baudRate
     * @param dataBits
     * @param stopBits
     * @param parity
     */
    public PortConfig(String portName, Integer baudRate, Integer dataBits, Integer stopBits, Integer parity) {
        this.portName = portName;
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
        setted = true;
    }

    /**
     * Indica si fue inicializada la configuracion
     * @return verdadero si la configuración está inicializada
     */
    public boolean isSetted() {
        return setted;
    }

    /**
     * BaudRate del puerto
     * @return 
     */
    public Integer getBaudRate() {
        return baudRate;
    }

    /**
     * BaudRate del puerto
     * @param baudRate 
     */
    public void setBaudRate(Integer baudRate) {
        setted = true;
        this.baudRate = baudRate;
    }

    /**
     * DataBits del puerto
     * @return 
     */
    public Integer getDataBits() {
        return dataBits;
    }

    /**
     * DataBits del puerto
     * @param dataBits 
     */
    public void setDataBits(Integer dataBits) {
        setted = true;
        this.dataBits = dataBits;
    }

    /**
     * Paridad
     * @return 
     */
    public Integer getParity() {
        return parity;
    }

    /**
     * Paridad
     * @param parity 
     */
    public void setParity(Integer parity) {
        setted = true;
        this.parity = parity;
    }

    /**
     * Bits de parada
     * @return 
     */
    public Integer getStopBits() {
        return stopBits;
    }

    /**
     * Bits de parada
     * @param stopBits 
     */
    public void setStopBits(Integer stopBits) {
        setted = true;
        this.stopBits = stopBits;
    }

    /**
     * Nombre del puerto<br/>
     * /dev/tty[S|USB]n en *nix<br/>
     * COMn en windows
     * @return 
     */
    public String getPortName() {
        return portName;
    }

    /**
     * Nombre del puerto<br/>
     * /dev/tty[S|USB]n en *nix<br/>
     * COMn en windows 
     * @param portName
     */
    public void setPortName(String portName) {
        setted = true;
        this.portName = portName;
    }

    @Override
    public String toString() {
        return "PortConfig{" + "baudRate=" + baudRate + ", dataBits=" + dataBits + ", stopBits=" + stopBits + ", parity=" + parity + ", setted=" + setted + ", portName=" + portName + '}';
    }

}
