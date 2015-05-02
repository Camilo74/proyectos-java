package ar.com.qestudio.server.exception;

public class BussinessException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BussinessException(String message){
		super(message);
	}
	
	public BussinessException(String message, Throwable cause){
		super(message,cause);
	}
}
