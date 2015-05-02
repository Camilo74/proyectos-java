package ar.com.qestudio.server.socket.process;

import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import ar.com.qestudio.core.model.mensaje.impl.Callback;
import ar.com.qestudio.server.socket.api.APIService;

public class ProcessCallback {

	private final static Logger logger = Logger.getLogger(ProcessCallback.class);
	
	public ProcessCallback(Callback callback, ObjectOutputStream out, APIService service) {
		try {
			Method method = service.getClass().getDeclaredMethod(callback.getFunctionName());
			Object value = method.invoke(service);
			callback.setValue(value);
			out.writeObject(callback);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}