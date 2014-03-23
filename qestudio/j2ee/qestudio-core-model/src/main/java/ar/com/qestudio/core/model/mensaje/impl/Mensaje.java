package ar.com.qestudio.core.model.mensaje.impl;

import java.util.ArrayList;
import java.util.List;

import ar.com.qestudio.core.model.Contacto;
import ar.com.qestudio.core.model.mensaje.IMessage;

public class Mensaje implements IMessage {

	private static final long serialVersionUID = 1949388280696179950L;

	private Contacto contacto; // origen
	private String texto;
	private List<Contacto> destinos; // Si no se seleccion
	private Boolean leido;
	
	public Mensaje(){
		destinos = new ArrayList<Contacto>();
		leido = false;
	}
	
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTexto() {
		return texto;
	}

	public List<Contacto> getDestinos() {
		return destinos;
	}

	public void setDestinos(List<Contacto> destinos) {
		this.destinos = destinos;
	}
	
	@Override
	public String toString(){
		return texto; 
	}

	public Boolean getLeido() {
		return leido;
	}

	public void setLeido(Boolean leido) {
		this.leido = leido;
	}

	public void addDestino(Contacto contacto) {
		destinos.add(contacto);
	}

}