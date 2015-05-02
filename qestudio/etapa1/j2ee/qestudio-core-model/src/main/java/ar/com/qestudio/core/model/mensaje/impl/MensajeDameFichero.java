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
 * Mensaje para pedir un fichero.
 * @author Javier Abell�n
 *
 */
public class MensajeDameFichero implements IMessage {
	
	private static final long serialVersionUID = -1366373469901981573L;
	
	/** path completo del fichero que se pide */
    public String nombreFichero;
    
    public String temporal;
}
