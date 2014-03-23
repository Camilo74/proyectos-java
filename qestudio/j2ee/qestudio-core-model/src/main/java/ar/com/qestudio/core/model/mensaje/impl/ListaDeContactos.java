package ar.com.qestudio.core.model.mensaje.impl;

import java.util.List;

import ar.com.qestudio.core.model.Contacto;
import ar.com.qestudio.core.model.mensaje.IMessage;

public class ListaDeContactos implements IMessage {

	private static final long serialVersionUID = -7531940654325569957L;
	private List<Contacto> usuarios; // Si no se seleccion

	public ListaDeContactos() {
	}

	public ListaDeContactos(List<Contacto> conectados){
		this.usuarios = conectados;
	}

	public List<Contacto> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Contacto> usuarios) {
		this.usuarios = usuarios;
	}

}