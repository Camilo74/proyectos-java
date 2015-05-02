package ar.com.qestudio.core.model.mensaje.impl;

import ar.com.qestudio.core.model.mensaje.IMessage;


public class Error implements IMessage {

	private static final long serialVersionUID = -6036276362104234786L;
	
	private Exception excepcion;

	public Exception getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(Exception excepcion) {
		this.excepcion = excepcion;
	}
	
	@Override
	public String toString(){
		return excepcion.getMessage();
	}
	
}