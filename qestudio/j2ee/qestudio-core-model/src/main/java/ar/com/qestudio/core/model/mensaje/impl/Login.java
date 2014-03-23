package ar.com.qestudio.core.model.mensaje.impl;

import ar.com.qestudio.core.model.Contacto;
import ar.com.qestudio.core.model.mensaje.IMessage;

public class Login implements IMessage {

	private static final long serialVersionUID = 9149836013154774216L;
	private Contacto contacto; //origen
	
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public Contacto getContacto() {
		return contacto;
	}
	
	@Override
	public String toString(){
		return "user: " + contacto.getNick() + " ip: " + contacto.getIp();
	}
	
}