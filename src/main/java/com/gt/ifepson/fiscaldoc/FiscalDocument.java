package com.gt.ifepson.fiscaldoc;

import com.gt.ifepson.IFException;
import com.gt.ifepson.IFReturnValue;
import com.gt.ifepson.IfCommand;
import com.gt.ifepson.capa_fisica.DataType;
import com.gt.ifepson.capa_fisica.OutParam;
import com.gt.ifepson.capa_fisica.serialPort.PortConfig;
import com.gt.ifepson.capa_fisica.serialPort.SerialPortConnector;
import com.gt.ifepson.commands.AbrirCajonEfectivo;
import com.gt.ifepson.commands.AbrirDNF;
import com.gt.ifepson.commands.AbrirFNC;
import com.gt.ifepson.commands.AbrirTique;
import com.gt.ifepson.commands.AvanzaCintaTestigoTique;
import com.gt.ifepson.commands.AvanzaComprobanteTique;
import com.gt.ifepson.commands.AvanzaHojaSuelta;
import com.gt.ifepson.commands.AvanzaTique;
import com.gt.ifepson.commands.CerrarDNF;
import com.gt.ifepson.commands.CerrarFNC;
import com.gt.ifepson.commands.CerrarTique;
import com.gt.ifepson.commands.CierreX;
import com.gt.ifepson.commands.CierreXZ;
import com.gt.ifepson.commands.CierreZ;
import com.gt.ifepson.commands.CortarPapel;
import com.gt.ifepson.commands.DNFHTarjetaCredito;
import com.gt.ifepson.commands.GetFechaHora;
import com.gt.ifepson.commands.ImprItemFNC;
import com.gt.ifepson.commands.ImprItemTique;
import com.gt.ifepson.commands.ImprTxtDNF;
import com.gt.ifepson.commands.ImprTxtFiscTique;
import com.gt.ifepson.commands.LeePrefUsuario;
import com.gt.ifepson.commands.PagoCancelDescRecaFNC;
import com.gt.ifepson.commands.PagoCancelDescRecaTique;
import com.gt.ifepson.commands.enums.CalificadorPagoCancelDescReca;
import com.gt.ifepson.commands.PercepcionFNC;
import com.gt.ifepson.commands.PrepararEstacion;
import com.gt.ifepson.commands.RepoMemFiscFecha;
import com.gt.ifepson.commands.RepoMemFiscZ;
import com.gt.ifepson.commands.SelPrefUsuario;
import com.gt.ifepson.commands.SetDatoFijo;
import com.gt.ifepson.commands.SetFechaHora;
import com.gt.ifepson.commands.SolEstado;
import com.gt.ifepson.commands.SubtotalFNC;
import com.gt.ifepson.commands.SubtotalTique;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * Documento fiscal de varios comandos
 *
 * @author guillermot
 */
public class FiscalDocument {

    /**
     * tiempo de espera
     */
    protected int timeOut;

    /**
     * Configuración del puerto
     */
    protected PortConfig portConfig;

    /**
     * Lista de comandos a ejecutar
     */
    protected List<IfCommand> comandos = new ArrayList<IfCommand>();

    /**
     * Comandos disponibles
     */
    protected static Map<String, Class> comandosDisponibles = null;

    /**
     * Respuesta combinada
     */
    protected Map<OutParam, String> respuesta = new EnumMap<OutParam, String>(OutParam.class);
    //protected byte secuencia = 0x20;

    /**
     * String del batch tipo JPFBATCH
     */
    protected String batchOriginal = "";

    /**
     * Constructor
     */
    public FiscalDocument() {
    }

    /**
     * comandos disponibles para agregar a FiscalDocument, casi siempre
     * utilizado como ayuda
     *
     * @return
     */
    public static Map<String, Class> getComandosDisponibles() {

        if (comandosDisponibles == null) {
            comandosDisponibles = new HashMap<String, Class>();

            IfCommand com;
            com = new AbrirDNF();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new AbrirFNC();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new AbrirTique();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new AvanzaCintaTestigoTique();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new AvanzaComprobanteTique();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new AvanzaHojaSuelta();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new AvanzaTique();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new CerrarDNF();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new CerrarFNC();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new CerrarTique();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new CierreX();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new CierreXZ();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new CierreZ();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new CortarPapel();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new AbrirCajonEfectivo();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new DNFHTarjetaCredito();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new GetFechaHora();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new ImprItemFNC();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new ImprItemTique();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new ImprTxtDNF();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new ImprTxtFiscTique();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new LeePrefUsuario();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new PagoCancelDescRecaFNC();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new PagoCancelDescRecaTique();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new PercepcionFNC();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new PrepararEstacion();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new RepoMemFiscFecha();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new RepoMemFiscZ();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new SelPrefUsuario();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new SetDatoFijo();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new SetFechaHora();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new SolEstado();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new SubtotalFNC();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
            com = new SubtotalTique();
            comandosDisponibles.put(com.getNombreAbreviado(), com.getClass());
        }

        return comandosDisponibles;
    }

    /**
     * texto del batch original
     *
     * @return
     */
    public String getBatchOriginal() {
        return batchOriginal;
    }

    /**
     * texto del batch original
     *
     * @param batchOriginal
     */
    public void setBatchOriginal(String batchOriginal) {
        this.batchOriginal = batchOriginal;
    }

    /**
     * tiempo de espera
     *
     * @return
     */
    public int getTimeOut() {
        return timeOut;
    }

    /**
     * tiempo de espera
     *
     * @param timeOut
     */
    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * Configuración del puerto
     *
     * @return
     */
    public PortConfig getPortConfig() {
        return portConfig;
    }

    /**
     * Configuración del puerto
     *
     * @param config
     */
    public void setPortConfig(PortConfig config) {
        this.portConfig = config;
    }

    /**
     * Separa el String batch en lineas y llama IFBatch.fromLines
     *
     * @param batch
     * @param timeOut
     * @param config
     * @param separador
     * @param largoDesc
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static FiscalDocument fromBatchString(String batch, int timeOut, PortConfig config, String separador, int largoDesc) throws FileNotFoundException, IOException {
        String lines[] = batch.split("\n");

        return FiscalDocument.fromLines(lines, timeOut, config, separador,
                largoDesc);
    }

    /**
     * Lee el archivo fileName, lo separa en lineas y llama IFBatch.fromLines
     *
     * @param fileName
     * @param timeOut
     * @param config
     * @param separador
     * @param largoDesc
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static FiscalDocument fromFile(String fileName, int timeOut, PortConfig config, String separador, int largoDesc) throws FileNotFoundException, IOException {

        BufferedReader entrada = new BufferedReader(new FileReader(new File(
                fileName)));

        List<String> lineas = new ArrayList<String>();
        String linea;

        while ((linea = entrada.readLine()) != null) {
            lineas.add(linea);
        }

        return FiscalDocument.fromLines(lineas.toArray(new String[]{}),
                timeOut, config, separador, largoDesc);
    }

    /**
     * Parsea cada line en un comando individual para generar el Batch<br/>
     * Además carga cada linea en un texto batchOriginal para usos en log
     *
     * @param lines
     * @param timeOut
     * @param config
     * @param separador
     * @param largoDesc
     * @return
     */
    public static FiscalDocument fromLines(String[] lines, int timeOut, PortConfig config, String separador, int largoDesc) {
        FiscalDocument retVal = new FiscalDocument();
        retVal.setTimeOut(timeOut);
        retVal.setPortConfig(config);

        IfCommand comm;

        for (String linea : lines) {
            if (linea.trim().length() > 0) {
                retVal.batchOriginal += linea + "\n";
                comm = FiscalDocument.parseCommand(linea.trim(), separador, largoDesc);
                if (comm == null) {
                    Logger.getLogger(FiscalDocument.class).log(Level.ERROR, "No se pudo generar gomando de " + linea.trim());
                } else {
                    comm.setTimeOut(retVal.getTimeOut());
                    Logger.getLogger(FiscalDocument.class).log(Level.DEBUG, "Setting timeout en " + comm.getNombreAbreviado() + " = " + retVal.getTimeOut());
                    retVal.addCommand(comm);
                }
            }
        }

        return retVal;
    }

    /**
     * Devuelve un mapa con las respuestas indexadas y su valor
     *
     * @return
     */
    public Map<OutParam, String> getRespuesta() {
        return respuesta;
    }

    /**
     * Establece la respuesta
     *
     * @param respuesta
     */
    public void setRespuesta(Map<OutParam, String> respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * Devuelve una lista con los comandos del batch
     *
     * @return
     */
    public List<IfCommand> getComandos() {
        return comandos;
    }

    /**
     * Establece la lista de comandos
     *
     * @param comandos
     */
    public void setComandos(List<IfCommand> comandos) {
        this.comandos = comandos;
    }

    /**
     * Agrega un comando a la lista de comandos
     *
     * @param comando
     */
    public void addCommand(IfCommand comando) {
        comandos.add(comando);
    }

    /**
     * Envía los comandos en orden al controlador fiscal
     */
    public void run() {

        IFReturnValue retComm = IFReturnValue.OK;

        SerialPort port = new SerialPortConnector(portConfig).getSerialPort();

        boolean error = false;

        byte secuencia = 0x20;

        for (IfCommand comm : comandos) {

            Logger.getLogger(FiscalDocument.class).log(Level.DEBUG, "Enviando comando " + comm.getNombreAbreviado() + "  nro de serie:  " + comm.getSecuencia());

            try {
                retComm = comm.ejecutar(port, secuencia);
                secuencia++;
                if (secuencia >= 0x7f) {
                    secuencia = 0x20;
                }

            } catch (IFException ex) {
                retComm = IFReturnValue.UNKNOW_ERROR;
                this.respuesta.put(OutParam.OTROS_ERRORES, ex.getMessage());
                Logger.getLogger(FiscalDocument.class).log(Level.ERROR, null, ex);
            } catch (SerialPortException ex) {
                retComm = IFReturnValue.SERIAL_PORT_UNSOPORTED_OP_ERROR;
                this.respuesta.put(OutParam.PUERTO_SERIE, "Error en puerto serie");
                Logger.getLogger(FiscalDocument.class).log(Level.ERROR, null, ex);
            }

            Logger.getLogger(FiscalDocument.class).log(Level.DEBUG, "Combinando las respuestas de los comandos individuales");

            combinarRespuesta(comm.getRespuesta());

            if (retComm != IFReturnValue.OK) {

                Logger.getLogger(FiscalDocument.class).log(Level.ERROR, "Error ejecutando " + comm.getNombreAbreviado() + " el comando devolvio " + retComm.getDescription());

                comm.log(Level.FATAL);

                error = true;
                break;
            }

        }

        Logger.getLogger(FiscalDocument.class).log(Level.DEBUG, "Fin de comandos IFBatch con retComm " + retComm);

        if (error) {
            this.log(Level.FATAL);
        }

        this.checkEnd(port);

        if (port != null) {
            try {
                Logger.getLogger(FiscalDocument.class).log(Level.DEBUG, "\nCerrando Puerto\n");
                port.closePort();
            } catch (SerialPortException ex) {
                Logger.getLogger(FiscalDocument.class).log(Level.ERROR, ex.getMessage(), ex);
            }
        }

    }

    /**
     * Loguea
     *
     * @param priority
     */
    protected void log(Priority priority) {

        StringBuilder sb = new StringBuilder();

        sb.append("\n\n\n\n[C O M I E N Z O    E R R O R] \n\n\n\nError ejecutando batch con comandos\n");

        for (IfCommand comm : comandos) {
            sb.append(comm.getLog());
        }

        sb.append("Las respuestas combinadas hasta el momento del error son\n");

        for (OutParam op : respuesta.keySet()) {
            sb.append("\t").append(op.name()).append(" ").append(op.getDescripcion()).append(" = ").append(respuesta.get(op)).append("\n");
        }

        sb.append("\n\n\n\n[F I N    E R R O R] \n\n\n\n");

        Logger.getLogger(FiscalDocument.class).log(priority, sb.toString());
    }

    /**
     * Chequea que el documento abierto se cierre
     *
     * @param port
     * @return
     */
    protected IFReturnValue checkEnd(SerialPort port) {

        IFReturnValue retComm = IFReturnValue.OK;

        IfCommand fin = new SolEstado();

        byte secuencia = 0x7f;

        try {
            Logger.getLogger(FiscalDocument.class).log(Level.DEBUG, "Enviando comando con serie " + fin.getSecuencia() + " para que sincronice");
            retComm = fin.ejecutar(port, secuencia);

            Logger.getLogger(FiscalDocument.class).log(Level.DEBUG,
                    "fin volvio con " + retComm);
        } catch (IFException ex) {
            this.respuesta.put(OutParam.OTROS_ERRORES, ex.getMessage());
            Logger.getLogger(FiscalDocument.class).log(Level.ERROR, null, ex);
        } catch (SerialPortException ex) {
            this.respuesta.put(OutParam.OTROS_ERRORES, ex.getMessage());
            Logger.getLogger(FiscalDocument.class).log(Level.ERROR, ex.getMessage(), ex);
        }

        if (retComm != IFReturnValue.OK
                && this.comandos.size() > 0
                && this.respuesta.containsKey(OutParam.EF__DOCUM_FISC_ABIERTO)) {

            Logger.getLogger(FiscalDocument.class).log(Level.DEBUG, "rutina de salvado de errores en IFBatch");

            retComm = IFReturnValue.OK;

            IfCommand comm = null;
            try {
                // un error en la ejecucion
                // trato de cancelar el tique en caso que se pueda
                if (comandos.get(0).getNombreAbreviado().equals("TIQUEABRE")) {

                    Logger.getLogger(FiscalDocument.class).log(Level.DEBUG, "Intentando cancelar comprobante tiquet");

                    comm = new com.gt.ifepson.commands.PagoCancelDescRecaTique();
                    ((com.gt.ifepson.commands.PagoCancelDescRecaTique) comm).setCalificador(CalificadorPagoCancelDescReca.CANCELAR_COMPROBANTE);

                }

                if (comandos.get(0).getNombreAbreviado().equals("FACTABRE")) {

                    Logger.getLogger(FiscalDocument.class).log(Level.DEBUG, "Intentando cancelar comprobante factura / nota credito");

                    comm = new com.gt.ifepson.commands.PagoCancelDescRecaFNC();
                    ((com.gt.ifepson.commands.PagoCancelDescRecaFNC) comm).setCalificador(CalificadorPagoCancelDescReca.CANCELAR_COMPROBANTE);

                }

                if (comm != null) {
                    retComm = comm.ejecutar(port, (byte) 0x20);
                }
            } catch (IFException ex) {
                retComm = IFReturnValue.UNKNOW_ERROR;
                this.respuesta.put(OutParam.OTROS_ERRORES, ex.getMessage());
                Logger.getLogger(FiscalDocument.class).log(Level.ERROR, null, ex);
            } catch (SerialPortException ex) {
                retComm = IFReturnValue.SERIAL_PORT_UNSOPORTED_OP_ERROR;
                this.respuesta.put(OutParam.OTROS_ERRORES, ex.getMessage());
                Logger.getLogger(FiscalDocument.class).log(Level.ERROR, ex.getMessage(), ex);
            }

            if (comm != null) {
                combinarRespuesta(comm.getRespuesta());
            }

            if (retComm != IFReturnValue.OK) {
                Logger.getLogger(FiscalDocument.class).log(Level.ERROR, "NO SE PUDO SALVAR EL ERROR ");
            }

        }

        return retComm;
    }

    /**
     * Combina la respuesta resp con la que tiene
     *
     * @param resp
     */
    private void combinarRespuesta(Map<OutParam, String> resp) {
        for (OutParam key : resp.keySet()) {
            respuesta.put(key, resp.get(key));
        }
    }

    /**
     * devuelve el serial incrementado
     *
     * @return public byte getSerial() { secuencia++;
     *
     * if (secuencia < 0x20) { secuencia = 0x20; } if (secuencia >= 0x7F) {
     * secuencia = 0x20; }
     *
     * return secuencia; }
     *
     */
    /**
     * Transforma una linea de texto en un comando fiscal
     *
     * @param linea
     * @param separador
     * @param largoDesc
     * @return
     */
    public static IfCommand parseCommand(String linea, String separador, int largoDesc) {

        String[] parser = linea.split(separador);

        Logger.getLogger(FiscalDocument.class).log(
                Level.DEBUG,
                "Separando comando " + linea + " con separador " + separador
                + " y queda como comando:" + parser[0]);

        if (largoDesc == 0) {
            largoDesc = 16;
        }

        Logger.getLogger(FiscalDocument.class).log(
                Level.DEBUG,
                "Seteando largo maximo de descripcion para Tique y TiqueFact en "
                + largoDesc);

        com.gt.ifepson.capa_fisica.InParam.IIT__DESCRIPCION_PROD.setLargo(largoDesc);
        com.gt.ifepson.capa_fisica.InParam.IIFNC__DESCRIPCION_PROD.setLargo(largoDesc);

        IfCommand comm = null;

        for (String commName : FiscalDocument.getComandosDisponibles().keySet()) {

            if (("@" + commName).toUpperCase().equals(parser[0].toUpperCase())) {
                try {

                    ClassLoader clazzLoader;
                    Class<?> clazz;
                    // try {
                    clazzLoader = ClassLoader.getSystemClassLoader();

                    clazz = clazzLoader.loadClass(FiscalDocument.getComandosDisponibles().get(commName).getName());

                    // comm = (IfCommand)
                    // myjob.func.classutils.ClassFunc.getInstanceFromClassName(comandos.get(commName).getName());
                    comm = (IfCommand) clazz.newInstance();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FiscalDocument.class).log(Level.FATAL,
                            "Clase no encontrada", ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(FiscalDocument.class).log(Level.FATAL,
                            "No se pudo crear instancia de clase", ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(FiscalDocument.class).log(Level.FATAL,
                            "Acceso ilegal", ex);
                }
                break;
            }
        }

        if (comm != null) {

            Logger.getLogger(FiscalDocument.class).log(Level.DEBUG, "Agregando comando " + comm.getNombreAbreviado());

            for (int pos = 1; pos <= comm.getPosiblesParams().size()
                    && pos < parser.length; pos++) {

                String paramVal = parser[pos].trim();

                if (paramVal.length() > comm.getIndexedParam(pos - 1).getLargo()) {
                    if (comm.getIndexedParam(pos - 1).getType() == DataType.Integer
                            || comm.getIndexedParam(pos - 1).getType() == DataType.Num2Dec
                            || comm.getIndexedParam(pos - 1).getType() == DataType.Num3Dec
                            || comm.getIndexedParam(pos - 1).getType() == DataType.Num4Dec
                            || comm.getIndexedParam(pos - 1).getType() == DataType.Num8Dec) {
                        paramVal = paramVal.substring(paramVal.length() - comm.getIndexedParam(pos - 1).getLargo());
                    } else {
                        paramVal = paramVal.substring(0, paramVal.length());
                    }
                }

                comm.setParamValue(pos - 1, paramVal);

            }
        }

        return comm;
    }

    @Override
    public String toString() {
        String retVal = "IFBatch{" + "timeOut=" + timeOut + ", portConfig="
                + portConfig + "\n";

        retVal += "\tORIGINAL:\n" + this.batchOriginal + "\tFIN ORIGINAL\n";

        for (int i = 0; i < comandos.size(); i++) {
            retVal += "\t[" + i + "]"
                    + comandos.get(i).toString().replace("\n", "\n\t") + "\n";
        }

        return retVal + "}";
    }

    /**
     * sortea la secuencia del comando, no se utiliza mas
     *
     * @return
     */
    @Deprecated
    byte sortearSecuencia() {
        byte secuencia = 0;
        while (secuencia < 0x20 || secuencia > 0x7f) {
            Random aleatorio = new Random();
            byte sorteo = 0;

            do {
                sorteo = (byte) (aleatorio.nextInt(127 - 32) + 32);
            } while (sorteo == secuencia);
            secuencia = sorteo;

        }
        return secuencia;
    }

}
