package ar.com.qestudio.server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Servidor con hilos 
 * El servidor es un hilo y los clientes otros..
 */
public class ServerManager implements ApplicationContextAware {
	
	private final static Logger logger = Logger.getLogger(ServerManager.class);
	
	private ServerSocket socket;
	private ApplicationContext applicationContext;
	
	public void execute(int port) throws IOException {
		logger.info("Abriendo servidor en puerto " + port);
		socket = new ServerSocket(port);
		while (true) {
			logger.info("Esperando una conexion");
			try {
				Socket conexion = socket.accept();
				logger.info("Conexion recibida");
				//le pedimos al cotexto de spring un nuevo gestor de conexiones para que maneje la nueva conexion
				SocketManager socketManager = (SocketManager) applicationContext.getBean("socket.manager",conexion);
				//inicial el nuevo hilo en forma asincronica
				socketManager.start();
			}
			catch (Exception e) {
				logger.error(e.getMessage());
				break;
			}
		}
		logger.info("Servidor finalizado");
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}