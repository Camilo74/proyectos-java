package ar.com.qestudio.client.socket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.com.qestudio.core.model.Attached;
import ar.com.qestudio.core.model.Registry;
import ar.com.qestudio.core.model.mensaje.impl.Callback;
import ar.com.qestudio.core.model.mensaje.impl.MensajeDameFichero;
import ar.com.qestudio.core.model.mensaje.impl.MensajeTomaFichero;

// InnerClass para el manejo de conexiones
public class SocketManager extends Thread {

	private final static Logger logger = Logger.getLogger(SocketManager.class);
	
	private Socket conexion;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private List<Callback> callbacks = new ArrayList<Callback>();
	private FileOutputStream fileOutputStream;
	
	private File file;
	
	public SocketManager(){
		super();
	}
	
	public SocketManager(Socket conexion) {
		try {
			this.conexion = conexion;
			this.salida = new ObjectOutputStream(conexion.getOutputStream());
			this.entrada = new ObjectInputStream(conexion.getInputStream());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void run() {
		logger.info("Atendiendo la conexion del servidor...");
		try {
			while (!getEstadoConexion()) {
				Object mensaje = entrada.readObject();
				Method method = this.getClass().getDeclaredMethod("process", new Class[]{mensaje.getClass()});
				method.invoke(this, mensaje);
			}
		} catch (Exception e) {
			logger.info("Error: desconectando del servidor ");
		}
	}
	
	@SuppressWarnings("unused")
	private void process(Callback callback){
		callbacks.add(callback);
	}
	
	@SuppressWarnings("unused")
	private void process(MensajeTomaFichero mensajeRecibido){
		try {
			if(!mensajeRecibido.ultimoMensaje){
				fileOutputStream.write(mensajeRecibido.contenidoFichero, 0, mensajeRecibido.bytesValidos);
			}else{
				fileOutputStream.close();
				file = new File(mensajeRecibido.temporal);
				openDefaultAplication(file);
//				System.out.println("1) esta abierto: " + isOpen(file));
//				while (isOpen(file)) {
//					System.out.println("2) esta abierto: " + isOpen(file));
//					Thread.sleep(3000);
//				};
//				System.out.println("3) esta abierto: " + isOpen(file));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openDefaultAplication(File file) {
		try {
//			Desktop.getDesktop().open(file);
			Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file.getAbsolutePath());
			System.out.println("Done." + p.waitFor());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			String filename = "C:\\qestudio-temp\\Empresa2\\2\\Shoptime.com2.pdf";
			File file = new File(filename);
			SocketManager socketManager = new SocketManager();
			socketManager.openDefaultAplication(file);
			
//			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw"); 
//			FileChannel fileChannel = randomAccessFile.getChannel();
//			FileLock fileLock = fileChannel.tryLock();
//			Desktop.getDesktop().open(file);
//			int cdor = 0;
//			while (cdor++ < 20) {
//				Thread.sleep(5000);
//				System.out.println("esperando");
//			}
//			System.out.println("LIBERANDO EL ARCHIVO");
//			fileLock.release();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	private boolean isOpen(File file) {
//		File sameFileName = new File(file.getAbsolutePath());
//		if (file.renameTo(sameFileName)) {
//			return false;
//		}
//		return true;
//	}

	public void openFile(Registry registry, Attached attached) {
		try {
			MensajeDameFichero mensaje = new MensajeDameFichero();
			mensaje.nombreFichero = attached.getFullPath();
			mensaje.temporal = "C:\\qestudio-temp\\" + registry.getInterested().getName() + File.separator + registry.getId() +File.separator + attached.getName();
			fileOutputStream = new FileOutputStream(mensaje.temporal);
			salida.writeObject(mensaje);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public Object call(String functionName, Object ... parameters){
		Callback callback = new Callback();
		callback.setFunctionName(functionName);
		callback.setParameters(parameters);
		try {
			salida.writeObject(callback);
			logger.info("despues de salida");
			boolean seguir = false;
			while(!seguir) {
				Thread.sleep(50);
				seguir = existResult(callback.getId());
			}
			callback = getResult(callback.getId());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return callback.getValue();
	}

	private Boolean getEstadoConexion() {
		return conexion.isClosed();
	}
	
	private boolean existResult(long id){
		Callback call = new Callback();
		call.setId(id);
		return callbacks.contains(call);
	}
	
	private Callback getResult(long id){
		Callback call = new Callback();
		call.setId(id);
		int index = callbacks.indexOf(call);
		return callbacks.get(index);
	}

	public Socket getConexion() {
		return conexion;
	}

	public void setConexion(Socket conexion) {
		this.conexion = conexion;
	}

	public ObjectInputStream getEntrada() {
		return entrada;
	}

	public void setEntrada(ObjectInputStream entrada) {
		this.entrada = entrada;
	}

	public ObjectOutputStream getSalida() {
		return salida;
	}

	public void setSalida(ObjectOutputStream salida) {
		this.salida = salida;
	}

	public List<Callback> getCallbacks() {
		return callbacks;
	}

	public void setCallbacks(List<Callback> callbacks) {
		this.callbacks = callbacks;
	}
}