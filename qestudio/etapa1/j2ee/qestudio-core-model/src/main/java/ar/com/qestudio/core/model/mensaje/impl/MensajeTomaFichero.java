/**
 * Javier Abell�n. 18 Mar 2006
 * 
 * Programa de ejemplo de como transmitir un fichero por un socket.
 * Esta es el mensaje que contiene los cachos de fichero que se van enviando
 * 
 */
package ar.com.qestudio.core.model.mensaje.impl;

import ar.com.qestudio.core.model.mensaje.IMessage;

/**
 * Mensaje que contiene parte del fichero que se est� transmitiendo.
 */
public class MensajeTomaFichero implements IMessage {
	
	private static final long serialVersionUID = -975894090605776414L;
	/** Nombre del fichero que se transmite. Por defecto "" */
    public String nombreFichero="";
    /** Si este es el ultimo mensaje del fichero en cuestion o hay mas despues */
    public boolean ultimoMensaje=true;
    /** Cuantos bytes son validos en el array de bytes */
    public int bytesValidos=0;
    /** Array con bytes leidos del fichero */
    public byte[] contenidoFichero = new byte[LONGITUD_MAXIMA];
    /** Numero maximo de bytes que se envian en cada mensaje */
    public final static int LONGITUD_MAXIMA=10;
	/** Nombre del fichero temporal */
    public String temporal;

    
}