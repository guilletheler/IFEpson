/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gt.ifepson;

import com.gt.ifepson.capa_fisica.DataType;
import com.gt.ifepson.capa_fisica.InParam;
import com.gt.ifepson.capa_fisica.OutParam;
import com.gt.ifepson.capa_fisica.serialPort.SerialPortReader;
import com.gt.ifepson.fiscaldoc.FiscalDocument;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 *
 * @author guillermot
 */
public abstract class IfCommand {

    //CONSTANTES DEL PROTOCOLO DEL IMPRESOR FISCAL
    
    /**
     * byte stx
     */
    public static final byte STX = 0x02;
    
    /**
     * Byte separador
     */
    public static final byte SEPARATOR = 0x1c;
    
    /**
     * byte etx
     */
    public static final byte ETX = 0x03;
    
    /**
     * byte nak
     */
    public static final byte NAK = 0x15;
    
    /**
     * bytes para agregar tiempo de espera
     */
    public static byte[] ADDTIME = new byte[]{0x12, 0x14};
    
    /**
     * intervalo de tiempo para esperar desde que se envia algo al puerto serie y se recibe algo del puerto, se resetea cada vez que el puerto recibe algo
     */
    public static final int TIME_INTERVAL = 800;
    
    /**
     * largo del bcc
     */
    public static final int BCC_LENGTH = 4;

    /**
     * cantidad maxima de nak
     */
    public static int CANT_NAK = 40;

    /**
     * cantidad maxima de reintentos
     */
    public static int reintentos = 3;

    /**
     * numero de secuencia del comando
     */
    protected byte secuencia = 0x0;
    
    /**
     * id del comando
     */
    protected byte commandId = 0x3a;
    
    /**
     * parametros de entrada del comando
     */
    protected Map<InParam, String> params = new EnumMap<InParam, String>(InParam.class);
    
    /**
     * lista de los posibles parametros de entrada
     */
    protected List<InParam> posiblesParams = null;
    
    /**
     * lista de los posibles parametros de salida
     */
    protected List<OutParam> posiblesSalidas = null;
    
    /**
     * respuesta obtenida luego de ejecutar el comando
     */
    protected Map<OutParam, String> respuesta = null;

    /**
     * nombre del comando
     */
    protected String name = "CommandName";
    
    /**
     * descripcion del comando
     */
    protected String descripcionComando = "";
    
    /**
     * tiempo de espera de ejecución del comando
     */
    protected int timeOut = 1000;
    
    /**
     * nombre corto, abreviado
     */
    protected String nombreAbreviado = "";
    
    /**
     * acción propuesta al llamante en caso de error
     */
    protected IFAction errorAction = IFAction.ABORT;

    /**
     * logueo
     * @param priority 
     */
    public void log(Priority priority) {
        Logger.getLogger(FiscalDocument.class).log(priority, this.getLog());
    }

    /**
     * String con una descripción del comando y sus parametros
     * @return 
     */
    public String getLog() {
        String tmpStr = "Comando " + this.getNombreAbreviado();

        tmpStr += " --" + this.getDescripcionComando() + "--";

        tmpStr += " con los siguientes parametros\n";

        for (InParam ip : this.getParams().keySet()) {
            tmpStr += "\t" + ip.getCodigo().toString() + "(" + ip.toString() + ") = " + this.getParams().get(ip) + "\n";
        }

        tmpStr += " las respuestas es\n";

        for (OutParam op : respuesta.keySet()) {
            tmpStr += "\t" + op.name() + " " + op.getDescripcion() + " = " + respuesta.get(op) + "\n";
        }

        return tmpStr;
    }

    /**
     * largo completo de la cadena de bytes generada de compilar el comando para enviarlo al puerto 
     * @return 
     */
    public int getLength() {

        // inicio, secuencia y comando
        int retVal = 3;

        for (InParam p : params.keySet()) {
            // 1 del separador mas el largo del campo

            retVal += 1;

            if (!params.get(p).equals("[--. N U L O .--]")) {
                if (params.get(p) != null) {
                    retVal += params.get(p).length();
                }
            }
        }

        // fin de texto + el bcc
        retVal += BCC_LENGTH + 1;

        return retVal;
    }

    /**
     * lista de los posibles parametros de entrada
     * @return 
     */
    public List<InParam> getPosiblesParams() {
        if (posiblesParams == null) {
            posiblesParams = new ArrayList<InParam>();

            for (InParam param : InParam.values()) {
                if (param.getComando() != null && param.getComando().isInstance(this)) {
                    posiblesParams.add(param);
                }
            }

        }
        return posiblesParams;
    }

    /**
     * lista de las posibles salidas
     * @return 
     */
    public List<OutParam> getPosiblesSalidas() {
        if (posiblesSalidas == null) {
            posiblesSalidas = new ArrayList<OutParam>();

            for (OutParam out : OutParam.values()) {
                if (out.getComando().isInstance(this)) {
                    posiblesSalidas.add(out);
                }
            }

        }
        return posiblesSalidas;
    }

    /**
     * devuelve el parametro de entrada según el índice
     * @param index
     * @return 
     */
    public InParam getIndexedParam(int index) {
        for (InParam p : getPosiblesParams()) {
            if (p.getIndice() == index) {
                return p;
            }
        }

        return null;
    }

    /**
     * setea el valor de un parametro
     * @param index
     * @param value
     * @return 
     */
    public InParam setParamValue(int index, String value) {

        for (InParam p : getPosiblesParams()) {
            if (p.getIndice() == index) {
                if (p.getType() == DataType.Alfa) {
                    String loc_Val = "";
                    for (int i = 0; i < value.length(); i++) {
                        if (value.charAt(i) < 32 || value.charAt(i) > 127) {
                            loc_Val += " ";
                        } else {
                            loc_Val += value.charAt(i);
                        }
                    }

                    //String spaces = (p.getLargo() == 0) ? "" : String.format("%" + p.getLargo() + "s", "");
                    loc_Val = loc_Val.trim();

                    if (loc_Val.isEmpty()) {
                        loc_Val = p.getDefaultValue();
                    }

                    Logger.getLogger(IfCommand.class).log(Level.DEBUG, "seteando parametro " + p.name() + " = " + loc_Val + "   {interpretado de '" + value + "'  ( largo:" + p.getLargo() + ")}");

                    params.put(p, loc_Val);

                    //params.put(p, spaces);
                } else {
                    Logger.getLogger(IfCommand.class).log(Level.DEBUG, "seteando parametro " + p.name() + " = " + value + "'  ( largo:" + p.getLargo() + ")");
                    String loc_Val = value;
                    if (value.trim().isEmpty()) {
                        loc_Val = p.getDefaultValue();
                    }
                    params.put(p, loc_Val);
                }

                return p;
            }
        }

        return null;

    }
    
    /**
     * devuelve el valor de un parametro como texto
     * @param pos
     * @return 
     */
    public String getParamValue(int pos) {
        return params.values().toArray(new String[] {})[pos];
        //return params.get(pos);
    }

    /**
     * devuelve los parametros de entrada
     * @return 
     */
    public Map<InParam, String> getParams() {
        return params;
    }

    /**
     * accion propuesta en caso de error
     * @return 
     */
    public IFAction getErrorAction() {
        return errorAction;
    }

    /**
     * accion propuesta en caso de error
     * @param errorAction 
     */
    public void setErrorAction(IFAction errorAction) {
        this.errorAction = errorAction;
    }

    /**
     * comando compilado
     * @return 
     */
    public byte[] getComandoBin() {
        byte[] retVal = new byte[getLength()];

        int pos = 0;

        retVal[pos++] = STX;
        retVal[pos++] = secuencia;
        retVal[pos++] = commandId;

        List<InParam> dictParam = new ArrayList<InParam>(params.keySet()); //List<Parametro>) params.keySet();

        Collections.sort(dictParam, new Comparator<InParam>() {

            @Override
            public int compare(InParam o1, InParam o2) {
                return o1.getIndice().compareTo(o2.getIndice());
            }
        });

        for (InParam p : dictParam) {

            retVal[pos++] = SEPARATOR;

            if (!params.get(p).equals("[--. N U L O .--]")) {

                retVal = IfCommand.writeInArray(retVal, params.get(p), pos);
                pos += params.get(p).length();
            }
        }

        retVal[pos++] = ETX;

        retVal = addBcc(retVal);

        return retVal;
    }

    /**
     * id del comando
     * @return 
     */
    public byte getCommandId() {
        return commandId;
    }

    /**
     * respuesta interpretada
     * @return 
     */
    public Map<OutParam, String> getRespuesta() {
        return respuesta;
    }

    /**
     * secuencia
     * @return 
     */
    public byte getSecuencia() {
        return secuencia;
    }

    /**
     * descripcion del comando
     * @return 
     */
    public String getDescripcionComando() {
        return descripcionComando;
    }

    /**
     * nombre corto, abreviado
     * @return 
     */
    public String getNombreAbreviado() {
        if (nombreAbreviado.length() == 0) {
            return name.toUpperCase();
        }
        return nombreAbreviado;
    }


    /**
     * reintentos
     * @return 
     */
    public static int getReintentos() {
        return reintentos;
    }

    /**
     * reintentos
     * @param reintentos 
     */
    public static void setReintentos(int reintentos) {
        IfCommand.reintentos = reintentos;
    }

    /**
     * timeout
     * @return 
     */
    public int getTimeOut() {
        return timeOut;
    }

    /**
     * timeout
     * @param timeOut 
     */
    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * Interpreta la respuesta de bajo nivel que viene desde el controlador
     * fiscal
     *
     * @param resp respuesta obtenida del impresor fiscal luego de enviar el
     * comando
     * @return verdadero si pudo interpretar la respuesta de manera correcta,
     * falso en caso contrario
     */
    protected abstract boolean interpretaRespuesta(byte[] resp);

    /**
     * Devuelve: 0 si esta todo bien 1 si el tiempo de espera esta agotado en
     * enviar comando 2 si el tiempo de espera esta agotado en recibir respuesta
     * 3 si la respuesta es vacia 4 error desconocido 5 error fiscal 6 error al
     * interpretar respuesta 15 no existe el puerto 16 puerto en uso 10 si NAK
     * 11 error de impresion 20 secuencia fuera de rango
     *
     * @param serialPort
     * @param secuencia
     * @return
     * @throws IFException
     * @throws jssc.SerialPortException
     */
    public IFReturnValue ejecutar(SerialPort serialPort, byte secuencia) throws IFException, SerialPortException {

        int reintento = 4;

        IFReturnValue retVal = IFReturnValue.OK;

        if (serialPort == null || !serialPort.isOpened()) {
            throw new IFException("Puerto nulo o no abierto");
        }

        this.secuencia = secuencia;

        do {
            reintento--;
            retVal = ejecutarInterno(serialPort);

        } while (retVal == IFReturnValue.NEED_RETRY && reintento > 0);

        return retVal;
    }

    /**
     *
     * @param serialPort
     * @return
     * @throws IFException
     * @throws jssc.SerialPortException
     */
    @SuppressWarnings("SleepWhileInLoop")
    protected IFReturnValue ejecutarInterno(SerialPort serialPort) throws IFException, SerialPortException {

        respuesta = new EnumMap<OutParam, String>(OutParam.class);

        int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
        serialPort.setEventsMask(mask);//Set mask

        SerialPortReader reader = new SerialPortReader(serialPort);

        serialPort.addEventListener(reader);//Add SerialPortEventListener

        byte[] comandoBin = this.getComandoBin();

        Logger.getLogger(IfCommand.class).log(Level.DEBUG, "\n"
                + "Se envio al controlador\n"
                + "              1         2         3         4         5         6         7         8         9         0         1         2         3\n"
                + "    01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890\n"
                + IfCommand.bytesPrimitiveToStringDebug(comandoBin));

        serialPort.writeBytes(comandoBin);

        //serialPort.purgePort(SerialPort.PURGE_RXCLEAR + SerialPort.PURGE_TXCLEAR);
        // reseteo el tiempo para comenzar recepcion
        reader.resetTime();

        while (!reader.isTimeOver() && !reader.isEnd()) {
            Logger.getLogger(IfCommand.class).log(Level.DEBUG, "Esperando respuesta, durmiendo thread ifCommand");
            //serialPort.purgePort(SerialPort.PURGE_RXCLEAR + SerialPort.PURGE_TXCLEAR);
            try {
                
                Thread.sleep(400);
            } catch (InterruptedException ex) {
                Logger.getLogger(IfCommand.class).log(Level.ERROR, "Error durmiendo thread de escucha de puerto", ex);
            }

        }

        // elimino el listener del puerto
        serialPort.removeEventListener();

        return this.analizarRespuesta(reader, comandoBin);

    }

    /**
     * analiza la respuesta binaria y la transforma en una serie de parametros de salida
     * @param reader
     * @param comandoBin
     * @return 
     */
    IFReturnValue analizarRespuesta(SerialPortReader reader, byte[] comandoBin) {
        byte[] respuestabin = reader.getRespuestaBin();

        Logger.getLogger(IfCommand.class).log(Level.DEBUG, "\n"
                + "y el Controlador respondió "
                + "              1         2         3         4         5         6         7         8         9         0         1         2         3\n"
                + "    01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890\n"
                + IfCommand.bytesPrimitiveToStringDebug(respuestabin));

        // fin de comunicación con el puerto
        //IFReturnValue retVal = IFReturnValue.OK;
        if (reader.isError()) {
            String tmp = "Error al intentar obtener respuesta\n";
            if (respuestabin != null) {
                tmp += "llegó hasta el momento del error: " + IfCommand.bytesPrimitiveToString(respuestabin);
            } else {
                tmp += "respuesta nula";
            }

            Logger.getLogger(IfCommand.class).log(Level.ERROR, tmp);

            if (!(respuestabin[1] == comandoBin[1] && respuestabin[2] == comandoBin[2])) {
                Logger.getLogger(IfCommand.class).log(Level.ERROR, "Comando con secuencia repetida con la anterior, sorteo otra secuencia, y devuelvo que se necesita reintentar");

                this.secuencia = 0;

                return IFReturnValue.NEED_RETRY;
            }

            if (reader.isNak()) {
                // pide que reenvie el comando como está
                return IFReturnValue.NEED_RETRY;
            }

            return IFReturnValue.UNKNOW_ERROR;

        }

        String respuestaToString = IfCommand.bytesPrimitiveToString(respuestabin);

        if (respuestaToString.toLowerCase().contains("error")) {
            this.respuesta.put(OutParam.SALIDA_IMPRESOR, respuestaToString.substring(respuestaToString.toLowerCase().indexOf("error"), respuestaToString.length() - 5));
        } else if (!interpretaRespuesta(respuestabin)) {
            Logger.getLogger(IfCommand.class).log(Level.ERROR, "Error al interpretar respuesta " + IfCommand.bytesPrimitiveToString(respuestabin));
            return IFReturnValue.UNKNOW_RESPONSE_ERROR;
        }

        Logger.getLogger(IfCommand.class).log(Level.DEBUG, "Analizando respuesta (estado fiscal y estado impresora)");
        // analizo la respuesta para sacar los estados fiscales y de impresora

        String estados = "";

        IFReturnValue tmp = this.analizarEstadoImpresora(EstadoImpresora.parseEstados(respuestabin, 4), estados);
        IFReturnValue tmp1 = this.analizarEstadoFiscal(EstadoFiscal.parseEstados(respuestabin, 9), estados);

        Logger.getLogger(IfCommand.class).log(Level.DEBUG, "Estado Obtenido: " + estados);

        if (tmp != IFReturnValue.OK) {
            // Error en estado impresora
            return tmp;
        }

        if (tmp1 != IFReturnValue.OK) {
            // Error en estado fiscal
            return tmp1;
        }

        Logger.getLogger(IfCommand.class).log(Level.DEBUG, "Fin IFCommand " + this.getNombreAbreviado() + " OK\n\n");

        return IFReturnValue.OK;
    }

    /**
     * Analiza el estado fiscal y lo agrega al resultado respuesta
     *
     * @param estadoFiscal
     * @param estados
     * @return
     */
    IFReturnValue analizarEstadoFiscal(Map<EstadoFiscal, String> estadoFiscal, String estados) {

        IFReturnValue retVal = IFReturnValue.OK;

        if (estadoFiscal != null) {
            for (EstadoFiscal ei : estadoFiscal.keySet()) {
                if (estados.length() > 0) {
                    estados += "\n";
                }
                estados += ei.getIndexedOut().getCod().toString() + ": Estado Fiscal: " + ei.toString();
                this.respuesta.put(ei.getIndexedOut(), "1");

                if (ei.toString().toLowerCase().contains("error")) {
                    Logger.getLogger(IfCommand.class).log(Level.ERROR, ei.getIndexedOut().getCod().toString() + ": Error fiscal");
                    retVal = IFReturnValue.FISCAL_ERROR;
                }

            }
        } else {
            Logger.getLogger(IfCommand.class).log(Level.ERROR, "Error al interpretar estado impresora ");
            this.respuesta.put(OutParam.OTROS_ERRORES, "Error al interpretar estado fiscal");
        }

        return retVal;
    }

    /**
     * Analiza el estado de la impresora y lo agrega al resultado respuesta
     *
     * @param estadoFiscal
     * @param estados
     * @return
     */
    IFReturnValue analizarEstadoImpresora(Map<EstadoImpresora, String> estadoImpresora, String estados) {

        IFReturnValue retVal = IFReturnValue.OK;

        if (estadoImpresora != null) {

            for (EstadoImpresora ei : estadoImpresora.keySet()) {
                if (estados.length() > 0) {
                    estados += "\n";
                }
                estados += ei.getIndexedOut().getCod().toString() + ": Estado Impresora: " + ei.toString();

                this.respuesta.put(ei.getIndexedOut(), "1");

                if (ei.toString().toLowerCase().contains("error")) {
                    Logger.getLogger(IfCommand.class).log(Level.ERROR, ei.getIndexedOut().getCod().toString() + ": Error de impresora");
                    retVal = IFReturnValue.PRINTER_ERROR;

                }

            }

        } else {
            Logger.getLogger(IfCommand.class).log(Level.ERROR, "Error al interpretar estado impresora ");
            this.respuesta.put(OutParam.OTROS_ERRORES, "Error al interpretar estado impresora");
        }

        return retVal;
    }

    /**
     * calcula el bcc
     * @param array
     * @return 
     */
    int getBcc(byte[] array) {
        int bcc = 0;

        for (int loc_Conta = 0; loc_Conta < array.length - BCC_LENGTH; loc_Conta++) {
            bcc += (int) array[loc_Conta];
        }

        return bcc;
    }

    /**
     * calcula y escribe el bcc al final del array, no modifica el largo del array
     * @param array
     * @return 
     */
    byte[] addBcc(byte[] array) {
        int bcc = getBcc(array);

        array = IfCommand.writeInArray(array, IfCommand.padLeft(Integer.toHexString(bcc).toUpperCase(), BCC_LENGTH, '0'), array.length - BCC_LENGTH);

        return array;
    }

    /**
     * nombre
     * @return 
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String retVal = "";
        try {
            retVal = "ifCommand{nombreA=" + nombreAbreviado + ", secuencia=" + secuencia + ", commandId=" + commandId + ", timeOut=" + timeOut + ", name=" + name + ", descripcionComando=" + descripcionComando + ",\n"
                    + "\tComandoBin=" + IfCommand.bytesPrimitiveToString(this.getComandoBin());

            for (InParam p : this.params.keySet()) {
                retVal += "\tparametro " + p.name() + " = " + params.get(p) + "\n";
            }
            if (this.respuesta != null) {
                for (OutParam io : this.respuesta.keySet()) {
                    retVal += "\trespuesta " + io.name() + " = " + respuesta.get(io) + "\n";
                }
            } else {
                retVal += "\trespuesta ES NULA!\n";
            }
            retVal += "}";
        } catch (Exception ex) {
            Logger.getLogger(IfCommand.class).log(Level.FATAL, "HAY ERROR en ifCommand.toString()", ex);
            retVal += "HAY ERROR en ifCommand.toString()";
        }

        return retVal;
    }

    /**
     * Posibles estados fiscales
     */
    public enum EstadoFiscal {

        /**
         * Error de comprobación de memoria fiscal
         */
        ErrCompMemFisc(new int[]{15 - 0, -15 + 7}, OutParam.EF__ERR_COMPROB_MEM_FISC),
        /**
         * Memoria fiscal llena
         */
        MemFiscFull(new int[]{15 - 0, 15 - 7}, OutParam.EF__MEM_FISC_LLENA),
        /**
         * Error de comprobación de memoria de trabajo
         */
        ErrCompMemTrab(new int[]{1}, OutParam.EF__ERR_COMPROB_MEM_TRAB),
        /**
         * batería baja
         */
        LowBat(new int[]{15 - 2}, OutParam.EF__POCA_BATERIA),
        /**
         * Comandop no reconocido
         */
        ComNoRecon(new int[]{15 - 3}, OutParam.EF__ERR_COMAND_NO_RECON),
        /**
         * Campo de datos inválido
         */
        CampDatInv(new int[]{15 - 4}, OutParam.EF__ERR_CAMPO_DATOS_INVALID),
        /**
         * Comando inválido para el estado fiscal
         */
        CampInvPEstFisc(new int[]{15 - 5}, OutParam.EF__ERR_COMANDO_INV_P_EST_FISC),
        /**
         * Desbordamiento de totales
         */
        DesbordTotales(new int[]{15 - 6, -15 + 11}, OutParam.EF__ERR_DESBORD_TOTALES),
        /**
         * Memoria fiscal casi llena
         */
        MemFiscCasiFull(new int[]{15 - 8}, OutParam.EF__MEM_FISC_CASI_LLENA),
        /**
         * Impresor fiscalizado
         */
        ImpFiscalizado(new int[]{15 - 9, 15 - 10}, OutParam.EF__IMPRES_FISCAL_FISCALIZADO),
        /**
         * Impresor certificado
         */
        ImpCertific(new int[]{15 - 9, -15 + 10}, OutParam.EF__IMPRES_FISCAL_CERTIFICADO),
        /**
         * Impresor desfiscalizado
         */
        ImpDesfisc(new int[]{-15 + 9, 15 - 10}, OutParam.EF__IMPRES_FISCAL_DESFISCALIZADO),
        /**
         * Se requiere un cierre Z
         */
        NececCierreZoTicket(new int[]{-15 + 6, 15 - 11}, OutParam.EF__NECESITA_Z),
        /**
         * Se necesita un transporte de hoja
         */
        NececTranspHoja(new int[]{15 - 6, 15 - 11}, OutParam.EF__ERR_NECESITA_TRANSPORTE_HOJA),
        /**
         * Documento fiscal abierto
         */
        DocFiscalAbierto(new int[]{15 - 12}, OutParam.EF__DOCUM_FISC_ABIERTO),
        /**
         * Documento fiscal abierto en rollo de papel
         */
        DocFiscalAbiertoRollo(new int[]{15 - 12, 15 - 13}, OutParam.EF__DOCUM_FISCAL_ABI_ROLLO_PAPEL),
        /**
         * Documento no fiscal abierto en rollo de papel
         */
        DocNoFiscalAbiertoRollo(new int[]{-15 + 12, 15 - 13}, OutParam.EF__DNF_ABIERTO_ROLLO_PAPEL),
        /**
         * Documento abierto en hoja suelta
         */
        DocAbiertoHojaSuelta(new int[]{15 - 14}, OutParam.EF__DOCUM_ABIERTO_HOJA_SUELTA),
        /**
         * Error
         */
        Error(new int[]{}, OutParam.EF__ERROR_ESTADO_FISCAL);
        
        int[] bitsIndex = null;
        OutParam detalle = null;

        EstadoFiscal(int[] bitsIndex, OutParam detalle) {
            this.bitsIndex = bitsIndex;
            this.detalle = detalle;
        }

        /**
         * índices del bit, donde inicia y termina
         * @return 
         */
        public int[] getBitsIndex() {
            return bitsIndex;
        }

        /**
         * Parametro de salida que representa
         * @return 
         */
        public OutParam getIndexedOut() {
            return detalle;
        }

        /**
         * Convierte un array de respuesta binaria en un mapa de estados fiscales
         * @param hexa
         * @return 
         */
        public static Map<EstadoFiscal, String> parseEstados(byte[] hexa) {
            StringBuilder sb = new StringBuilder();

            for (byte b : hexa) {
                sb.append((char) b);
            }

            return parseEstados(sb.toString());
        }

        /**
         * Convierte un array de respuesta binaria en un mapa de estados fiscales, comenzando en from
         * @param array
         * @param from
         * @return 
         */
        public static Map<EstadoFiscal, String> parseEstados(byte[] array, int from) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < 4; i++) {
                if ((from + i) >= array.length) {
                    // si pasa da ArrayIndexOutOfBoundsException
                    break;
                }

                sb.append((char) array[from + i]);

            }

            return parseEstados(sb.toString());
        }

        /**
         * Convierte una cadena de bytes hexadecimales de respuesta binaria en un mapa de estados fiscales
         * @param hexa
         * @return 
         */
        public static Map<EstadoFiscal, String> parseEstados(String hexa) {

            Map<EstadoFiscal, String> retVal;

            retVal = new EnumMap<EstadoFiscal, String>(EstadoFiscal.class);

            //int n = Integer.parseInt(hexa, 16);
            String binStr = IfCommand.padLeft(Integer.toBinaryString(Integer.parseInt(hexa, 16)), 16, '0');

            boolean add;

            for (EstadoFiscal curEst : EstadoFiscal.values()) {

                if (curEst.getBitsIndex().length > 0) {
                    add = true;

                    for (int idx : curEst.getBitsIndex()) {

                        if (idx > 0) {
                            // tiene que estar prendido el bit

                            add = add && binStr.charAt(idx) == '1';

                        } else {
                            // tiene que estar apagado el bit

                            add = add && binStr.charAt(Math.abs(idx)) == '0';
                        }
                    }

                    if (add) {
                        retVal.put(curEst, "1");
                    }
                }
            }

            for (int idx = 15; idx > 7; idx--) {
                if (binStr.charAt(idx) == '1') {
                    retVal.put(EstadoFiscal.Error, binStr);
                    break;
                }
            }

            if (!retVal.containsKey(EstadoFiscal.Error) && binStr.charAt(15 - 4) == '1') {
                retVal.put(EstadoFiscal.Error, binStr);
            }

            return retVal;
        }
    }

    /**
     * Estado de la impresoar
     */
    public enum EstadoImpresora {

        /**
         * Error en la impresora
         */
        ErrorDeImp(new int[]{15 - 2}, OutParam.EI__ERROR_EN_LA_IMPRESORA),
        /**
         * Fuera de linea
         */
        FueraLinea(new int[]{15 - 3}, OutParam.EI__IMPRES_FUERA_LINEA),
        /**
         * Buffer de impesora lleno
         */
        BufferLleno(new int[]{15 - 6}, OutParam.EI__BUFFER_LLENO),
        /**
         * Buffer de impresora vacío
         */
        BufferVacio(new int[]{15 - 7}, OutParam.EI__BUFFER_VACIO),
        /**
         * Entrada de hoja suelta frontal preparada
         */
        InHojaSuelPrep(new int[]{15 - 8}, OutParam.EI__ENTRADA_HOJ_SUEL_FRONT_PREP),
        /**
         * Hoja suelta frontal preparada
         */
        HojaSuelPrep(new int[]{15 - 9}, OutParam.EI__HOJ_SUEL_FROT_PREPAR),
        /**
         * Sin papel
         */
        SinPapel(new int[]{15 - 14}, OutParam.EI__SIN_PAPEL),
        /**
         * Poco papel
         */
        PocoPapel(new int[]{}, OutParam.EI__POCO_PAPEL),
        /**
         * Error en el estado de la impresora
         */
        Error(new int[]{}, OutParam.EI__ERROR_ESTADO_IMPRESORA);
        
        
        int[] bitsIndex = null;
        OutParam detalle = null;

        EstadoImpresora(int[] bitsIndex, OutParam detalle) {
            this.bitsIndex = bitsIndex;
            this.detalle = detalle;
        }

        /**
         * índice de la posición del parámetro
         * @return 
         */
        public int[] getBitsIndex() {
            return bitsIndex;
        }

        /**
         * Parametro de salida
         * @return 
         */
        public OutParam getIndexedOut() {
            return detalle;
        }

        /**
         * transforma un array de bytes de respuesta binaria en un mapa de respuestas
         * @param hexa
         * @return 
         */
        public static Map<EstadoImpresora, String> parseEstados(byte[] hexa) {
            StringBuilder sb = new StringBuilder();

            for (byte b : hexa) {
                sb.append((char) b);
            }

            return parseEstados(sb.toString());
        }

        /**
         * transforma un array de bytes de respuesta binaria en un mapa de respuestas, comenzando desde from
         * @param array
         * @param from
         * @return 
         */
        public static Map<EstadoImpresora, String> parseEstados(byte[] array, int from) {
            StringBuilder sb = new StringBuilder();

            for (int loc_Conta = 0; loc_Conta < 4; loc_Conta++) {
                sb.append((char) array[from + loc_Conta]);
            }

            return parseEstados(sb.toString());
        }

        /**
         * transforma una cadena con numero hexa de respuesta binaria en un mapa de respuestas
         * @param hexa
         * @return 
         */
        public static Map<EstadoImpresora, String> parseEstados(String hexa) {

            Map<EstadoImpresora, String> retVal = new EnumMap<EstadoImpresora, String>(EstadoImpresora.class);

            //int n = 0;
            //n = Integer.parseInt(hexa, 16);
            String binStr = IfCommand.padLeft(Integer.toBinaryString(Integer.parseInt(hexa, 16)), 16, '0');

            boolean add;

            for (EstadoImpresora curEst : EstadoImpresora.values()) {

                if (curEst.getBitsIndex().length > 0) {
                    add = true;

                    for (int idx : curEst.getBitsIndex()) {

                        if (idx > 0) {
                            // tiene que estar prendido el bit

                            add = add && binStr.charAt(idx) == '1';

                        } else {
                            // tiene que estar apagado el bit

                            add = add && binStr.charAt(Math.abs(idx)) == '0';
                        }
                    }

                    if (add) {
                        retVal.put(curEst, "1");
                    }
                }
            }

            for (int idx = 15; idx > 9; idx--) {
                if (binStr.charAt(idx) == '1') {
                    retVal.put(EstadoImpresora.Error, binStr);
                    break;
                }
            }

            if (!retVal.containsKey(EstadoImpresora.Error) && binStr.charAt(2) == '1') {
                retVal.put(EstadoImpresora.Error, binStr);
            }

            if (retVal.containsKey(EstadoImpresora.Error) && !retVal.containsKey(EstadoImpresora.ErrorDeImp) && !retVal.containsKey(EstadoImpresora.FueraLinea) && !retVal.containsKey(EstadoImpresora.BufferLleno) && !retVal.containsKey(EstadoImpresora.SinPapel)) {
                retVal.remove(EstadoImpresora.Error);
                retVal.put(EstadoImpresora.PocoPapel, "1");
            }

            return retVal;
        }
    }

// utilidades
    /**
     * Convierte un string en una fecha con el formato inFormat
     * @param date
     * @param inFormat
     * @return
     * @throws ParseException 
     */
    public static Date parseDate(String date, String inFormat) throws ParseException {
        DateFormat loc_df = new SimpleDateFormat(inFormat);

        if (date.trim().equals("00/00/0000")
                || date.trim().equals("00/00/00")
                || date.trim().length() == 0) {
            return loc_df.parse("01/01/1900");
        }

        if (inFormat.contains("/")) {
            date = date.replace("-", "/");
        }

        Date retVal;
        try {
            retVal = loc_df.parse(date.trim());
        } catch (ParseException ex) {
            System.out.println("imposible convertir " + date + " con mascara " + inFormat);
            throw ex;
        }

        return retVal;
    }

    /**
     * Convierte un array en una cadena de largo fijo, desde from con largo length
     * @param array
     * @param from
     * @param length
     * @return 
     */
    public static String extractConstStrFromArray(byte[] array, int from, int length) {

        StringBuilder sb = new StringBuilder();

        for (int loc_Conta = 0; loc_Conta < length; loc_Conta++) {
            sb.append((char) array[from + loc_Conta]);
        }

        return sb.toString();
    }

    /**
     * Convierte un array en una cadena de largo variable, desde from hasta que encuentre los caracteres ends
     * @param array
     * @param from
     * @param ends
     * @return 
     */
    public static String extractVarStrFromArray(byte[] array, int from, char... ends) {

        StringBuilder sb = new StringBuilder();

        boolean sale = false;

        for (int loc_Conta = 0; !sale; loc_Conta++) {

            if (from + loc_Conta >= array.length) {
                break;
            }

            for (int posend = 0; posend < ends.length; posend++) {
                if (ends[posend] == array[from + loc_Conta]) {
                    sale = true;
                    break;
                }
            }

            if (sale) {
                break;
            }

            sb.append((char) array[from + loc_Conta]);
        }

        return sb.toString();
    }

    /**
     * Devuelve un array de String tomando como fin el ultimo char de separators
     *
     * @param array
     * @param start
     * @param separators
     * @return
     */
    public static String[] bytesToStringArray(byte[] array, char start, char... separators) {
        List<String> retVal = new ArrayList<String>();

        StringBuilder token = new StringBuilder();

        boolean isStarted = false;

        Character end = separators[separators.length - 1];

        List<Character> separator = new ArrayList<Character>();

        for (int i = 0; i < (separators.length - 1); i++) {
            separator.add(separators[i]);
        }

        for (byte b : array) {
            if (b == end) {
                if (token.length() > 0) {
                    retVal.add(token.toString());
                }
                break;
            }

            if (isStarted) {

                if (separator.contains((Character) (char) b)) {
                    retVal.add(token.toString());
                    token = new StringBuilder();
                    continue;
                }

                token.append((char) b);
            }

            if (b == start) {
                isStarted = true;
            }
        }

        return retVal.toArray(new String[]{});
    }

    /**
     * busca el objeto o en el array
     * @param <T>
     * @param array
     * @param o
     * @return 
     */
    public static <T> boolean isIn(T[] array, T o) {
        for (T objInArray : array) {
            if (objInArray.equals(o)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Convierte un array de bytes en string
     * @param array
     * @return 
     */
    public static String bytesPrimitiveToString(byte[] array) {

        if (array == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (byte b : array) {
            sb.append((char) b);
        }

        return sb.toString();
    }

    
    /**
     * Convierte un array de Bytes en string
     * @param array
     * @return 
     */
    public static String bytesToString(Byte[] array) {

        if (array == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (byte b : array) {
            sb.append((char) b);
        }

        return sb.toString();
    }

    /**
     * Convierte un array de bytes en string, con información de depuración
     * @param array
     * @return 
     */
    public static String bytesPrimitiveToStringDebug(byte[] array) {

        StringBuilder sb = new StringBuilder();

        sb.append("\n    ");

        for (byte b : array) {
            if (b > 31 && b < 127) {
                sb.append((char) b);
            } else {
                sb.append(".");
            }
        }

        sb.append("\n    ");

        for (int i = 0; i < array.length; i++) {
            sb.append("[").append(String.format("%0$3d", i)).append("]");
        }

        sb.append("\n    ");

        char sale;

        for (byte b : array) {

            if (b > 31 && b < 127) {
                sale = (char) b;
            } else {
                sale = '.';
            }

            sb.append(String.format("%0$4s", (Character) sale)).append(" ");

        }

        sb.append("\ndec ");

        for (byte b : array) {
            sb.append("[").append(String.format("%0$3d", b)).append("]");
        }

        sb.append("\nhex ");

        for (byte b : array) {
            sb.append("[").append(String.format("%0$3s", Integer.toHexString((int) b))).append("]");
        }

        return sb.toString();
    }

    /**
     * Convierte un array de Bytes en string, con información de depuración
     * @param array
     * @return 
     */
    public static String bytesToStringDebug(Byte[] array) {

        StringBuilder sb = new StringBuilder();

        sb.append("\n    ");

        for (byte b : array) {
            if (b > 31 && b < 127) {
                sb.append((char) b);
            } else {
                sb.append(".");
            }
        }

        sb.append("\n    ");

        for (int i = 0; i < array.length; i++) {
            sb.append("[").append(String.format("%0$3d", i)).append("]");
        }

        sb.append("\n    ");

        char sale;

        for (byte b : array) {

            if (b > 31 && b < 127) {
                sale = (char) b;
            } else {
                sale = '.';
            }

            sb.append(String.format("%0$4d", sale)).append(" ");

        }

        sb.append("\ndec ");

        for (byte b : array) {
            sb.append("[").append(String.format("%0$3s", ((Byte) b).toString())).append("]");
        }

        sb.append("\nhex ");

        for (byte b : array) {
            sb.append("[").append(String.format("%0$3s", Integer.toHexString((int) b))).append("]");
        }

        return sb.toString();
    }

    /**
     * Inserta el valor value en el arreglo array comenzando en pos
     * @param array
     * @param value
     * @param pos
     * @return 
     */
    public static byte[] writeInArray(byte[] array, String value, int pos) {

        //System.out.println("Escribiendo " + value + " en " + pos);
        byte[] strVal = value.getBytes();
        System.arraycopy(strVal, 0, array, pos, value.length());

        return array;
    }

    /**
     * padright
     * @param string
     * @param n
     * @param padChar
     * @return 
     */
    public static String padRight(Object string, int n, char padChar) {

        if (n == 0 || n > 5000) {
            return "";
        }

        String s = string.toString();

        if (s.length() > n) {
            return s.substring(0, n - 1);
        }

        if (s.length() == n) {
            return s;
        }

        StringBuilder padded = new StringBuilder(s);

        while (padded.length() < n) {
            padded.append(padChar);
        }

        return padded.toString();
    }

    /**
     * padleft
     * @param string
     * @param n
     * @param padChar
     * @return 
     */
    public static String padLeft(Object string, int n, char padChar) {

        if (n == 0) {
            return "";
        }

        String s = string.toString();

        if (n < 0) {
            return s;
        }

        if (s.length() > n) {
            return s.substring(0, n - 1);
        }

        if (s.length() == n) {
            return s;
        }

        while (s.length() < n) {
            s = ((Character) padChar).toString() + s;
        }

        return s;
    }
}
