/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson.capa_fisica.serialPort;

import com.gt.ifepson.IfCommand;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Buffer de recepción de respuestas
 * @author guillermot
 */
public class SerialPortReader implements SerialPortEventListener {

    /**
     * 00000001
     */
    public static final byte FINALIZADO = 1;
    
    /**
     * 00000010
     */
    public static final byte ERROR_NAK = 2;

    /**
     * 00000100
     */
    public static final byte ERROR_SERIAL_PORT = 4;
    
    /**
     * 00001000
     */
    public static final byte ERROR_BCC = 8;
    
    SerialPort serialPort;
    List<Byte> recibido = new ArrayList<Byte>();
    int posBCC = 0;
    Calendar timeOut = Calendar.getInstance();

    byte estado = 0;

    /**
     * Constructor
     * @param serialPort 
     */
    public SerialPortReader(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    /**
     * Limpia lo recibido hasta el momento
     */
    public void clear() {
        this.recibido = new ArrayList<Byte>();
        this.estado = 0;
    }

    /**
     * Obtiene el último byte recibido
     * @return 
     */
    protected byte getLastByte() {
        if (recibido.size() > 0) {
            return recibido.get(recibido.size() - 1);
        }

        return 0;
    }

    /**
     * Resetea el tiempo que se utiliza como referencia para saber si debe dar un error de "tiempo de espera agotado"
     */
    public void resetTime() {
        this.timeOut = Calendar.getInstance();
        this.timeOut.add(Calendar.MILLISECOND, IfCommand.TIME_INTERVAL);
    }

    /**
     * Devuelve verdadero si se sobrepasó el tiempo de espera seteado
     * @return 
     */
    public boolean isTimeOver() {
        boolean retVal = !this.isEnd() && Calendar.getInstance().getTimeInMillis() > this.timeOut.getTimeInMillis();
        
        if(retVal) {
            Logger.getLogger(SerialPortReader.class).log(Level.ERROR, "Tiempo de espera agotado");
        }
        
        return retVal;
    }

    /**
     * Evento del puerto serie
     * @param event 
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR()) {
            try {
                this.analizarBuffer(serialPort.readBytes());

            } catch (SerialPortException ex) {
                this.estado = (byte) (this.estado | SerialPortReader.ERROR_SERIAL_PORT);
                Logger.getLogger(SerialPortReader.class).log(Level.ERROR, "Error recibiendo datos del puerto serie", ex);
            }

        } else if (event.isCTS()) {//If CTS line has changed state
            if (event.getEventValue() == 1) {//If line is ON
                System.out.println("CTS - ON");
            } else {
                System.out.println("CTS - OFF");
            }
        } else if (event.isDSR()) {///If DSR line has changed state
            if (event.getEventValue() == 1) {//If line is ON
                System.out.println("DSR - ON");
            } else {
                System.out.println("DSR - OFF");
            }
        }
    }

    /**
     * Analiza lo recibido
     * @param buffer 
     */
    protected void analizarBuffer(byte[] buffer) {
        
        if(buffer == null) {
            buffer = new byte[] {};
        }
        
        for (byte b : buffer) {
            if (b == IfCommand.ADDTIME[0] || b == IfCommand.ADDTIME[1]) {
                Logger.getLogger(IfCommand.class).log(Level.INFO, "pide mas tiempo, estado = " + this.estado);
                this.resetTime();
                continue;
            }

            if (b == IfCommand.NAK) {
                //pide que reenvie el mensaje
                this.estado = (byte) (this.estado + SerialPortReader.ERROR_NAK);
                this.estado = (byte) (this.estado + SerialPortReader.FINALIZADO);
                return;
            }

            // Finaliza el comando, los proximos son de bcc
            if (b == IfCommand.ETX || this.posBCC > 0) {
                this.posBCC++;
            }
            
            recibido.add(b);

            if (posBCC > 4) {
                this.estado = (byte) (this.estado + SerialPortReader.FINALIZADO);
                
                Logger.getLogger(IfCommand.class).log(Level.INFO, "recepcion finalizada, estado = " + this.estado);
                
                if(!checkBcc()) {
                    this.estado = (byte) (this.estado + SerialPortReader.ERROR_BCC);
                }
                
                break;
            }
        }
    }
    
    /**
     * Chequea bcc
     * @return 
     */
    protected boolean checkBcc() {

        int cur_bcc;
        StringBuilder sb = new StringBuilder();

        int pos = this.recibido.size() - 1;

        do {
            sb.insert(0, (char) (byte) this.recibido.get(pos));
            pos--;
        } while ((char) (byte) this.recibido.get(pos) != 0x03);

        cur_bcc = Integer.parseInt(sb.toString(), 16);

        if (cur_bcc == getBcc()) {
            return true;
        }
        
        Logger.getLogger(IfCommand.class).log(Level.INFO, "ERROR DE BCC " + cur_bcc + " != " + getBcc() );

        return false;
    }
    
    /**
     * Calcula el bcc
     * @return 
     */
    protected int getBcc() {
        int bcc = 0;

        for (int loc_Conta = 0; loc_Conta < this.recibido.size() - IfCommand.BCC_LENGTH; loc_Conta++) {
            bcc += (int) this.recibido.get(loc_Conta);
        }

        return bcc;
    }

    /**
     * genera un arreglo de bytes con la respuesta
     * @return 
     */
    public byte[] getRespuestaBin() {

        byte[] respuestabin = new byte[this.recibido.size()];

        for (int n = 0; n < this.recibido.size(); n++) {
            respuestabin[n] = this.recibido.get(n);
        }
        
        return respuestabin;
    }
    
    
    /**
     * Devuelve true si se estamos en el final de la recepción y no se espera recibir mas nada
     * @return 
     */
    public boolean isEnd() {
        return (this.estado & 1) != 0;
    }
    
    /**
     * Devuelve verdadero si se recibió algun error
     * @return 
     */
    public boolean isError() {
        return this.estado > 1;
    }
    
    /**
     * Devuelve verdadero si se recibió un nak
     * @return 
     */
    public boolean isNak() {
        return ((this.estado >> 1) & 1) != 0;
    }
     
    /**
     * Verdadero si se encontró un error del puerto serie
     * @return 
     */
    public boolean isSerialPortError() {
        return ((this.estado >> 2) & 1) != 0;
    }
    
    /**
     * verdadero si se encontró un error de bcc
     * @return 
     */
    public boolean isBccError() {
        return ((this.estado >> 3) & 1) != 0;
    }

    /**
     * configuración del puerto
     * @return 
     */
    public SerialPort getSerialPort() {
        return serialPort;
    }

    /**
     * configuración del puerto
     * @param serialPort 
     */
    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    /**
     * Lista de bytes con lo recibido
     * @return 
     */
    public List<Byte> getRecibido() {
        return recibido;
    }

    /**
     * Estado de la recepción
     * @return 
     */
    public byte getEstado() {
        return estado;
    }

}
