package ar.com.qestudio.server.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

import org.apache.log4j.Logger;

import ar.com.qestudio.core.model.mensaje.impl.Callback;
import ar.com.qestudio.core.model.mensaje.impl.MensajeDameFichero;
import ar.com.qestudio.server.socket.api.APIService;
import ar.com.qestudio.server.socket.process.ProcessCallback;
import ar.com.qestudio.server.socket.process.ProcessFile;

/**
 * para el manejo de conexiones
 * @author EP30873479
 */
public class SocketManager extends Thread {

	private final static Logger logger = Logger.getLogger(SocketManager.class);

	private Socket conexion;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	// declaro facade
	private APIService APIService;
	
	public SocketManager (Socket conexion){
		try {
			this.conexion = conexion;
			this.input = new ObjectInputStream(this.conexion.getInputStream());
			this.output = new ObjectOutputStream(this.conexion.getOutputStream());
		} catch (Exception e) {
			logger.info("Error al crear el objeto AtencionCliente");
		}		
	}
	
	public void run() {
		logger.info("Atendiendo la conexion...");
		try {
            while (!conexion.isClosed()){
				Object message = input.readObject();
				Method method = this.getClass().getDeclaredMethod("process", new Class[]{message.getClass()});
				method.invoke(this, message);
            }
		} catch (Exception e) {
			//Esta excepcion se puede dar cuando el usuario se desconecta
			logger.info("Error: desconectando usuario ");
			close();
		}
	}

	private void close(){
		logger.info("closeAll: Cerrando todas las conexiones del usuario");
		try {
			conexion.close();
			input.close();
			output.close();
		} catch (Exception e) {
			logger.info("closeAll: Error cerrando las conexiones del usuario");
		}
	}
	
    //*********************************************
    // Procesar mensajes
    //*********************************************
	
	@SuppressWarnings("unused")
	private void process(Callback callback){
		new ProcessCallback(callback, output, APIService);
	}

	@SuppressWarnings("unused")
	private void process(MensajeDameFichero mensaje){
		new ProcessFile(mensaje, output, APIService);
	}
	
	//-------------------------------

	public APIService getAPIService() {
		return APIService;
	}

	public void setAPIService(APIService apiService) {
		APIService = apiService;
	}

}