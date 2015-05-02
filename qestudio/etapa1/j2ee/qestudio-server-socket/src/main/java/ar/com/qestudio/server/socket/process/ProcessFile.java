package ar.com.qestudio.server.socket.process;

import java.io.FileInputStream;
import java.io.ObjectOutputStream;

import ar.com.qestudio.core.model.mensaje.impl.MensajeDameFichero;
import ar.com.qestudio.core.model.mensaje.impl.MensajeTomaFichero;
import ar.com.qestudio.server.socket.api.APIService;

public class ProcessFile {

	public ProcessFile(MensajeDameFichero fichero, ObjectOutputStream output, APIService service) {
		
		try {
			boolean enviadoUltimo = false;
			// Se abre el fichero.
			FileInputStream fis = new FileInputStream(fichero.nombreFichero);
			// Se instancia y rellena un mensaje de envio de fichero
			MensajeTomaFichero mensaje = new MensajeTomaFichero();
			mensaje.nombreFichero = fichero.nombreFichero;
			mensaje.temporal = fichero.temporal;
			// Se leen los primeros bytes del fichero en un campo del mensaje
			int leidos = fis.read(mensaje.contenidoFichero);
			// Bucle mientras se vayan leyendo datos del fichero
			while (leidos > -1) {
				// Se rellena el n�mero de bytes leidos
				mensaje.bytesValidos = leidos;
				// Si no se han leido el m�ximo de bytes, es porque el fichero
				// se ha acabado y este es el �ltimo mensaje
				if (leidos < MensajeTomaFichero.LONGITUD_MAXIMA) {
					mensaje.ultimoMensaje = true;
					enviadoUltimo = true;
				} else{
					mensaje.ultimoMensaje = false;
				}
				// Se env�a por el socket
				output.writeObject(mensaje);
				// Si es el �ltimo mensaje, salimos del bucle.
				if (mensaje.ultimoMensaje){
					break;
				}
				// Se crea un nuevo mensaje
				mensaje = new MensajeTomaFichero();
				mensaje.nombreFichero = fichero.nombreFichero;
				mensaje.temporal = fichero.temporal;
				// y se leen sus bytes.
				leidos = fis.read(mensaje.contenidoFichero);
			}

			if (enviadoUltimo == false) {
				mensaje.ultimoMensaje = true;
				mensaje.bytesValidos = 0;
				output.writeObject(mensaje);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}