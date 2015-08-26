package com.company.foo.model;

import javax.persistence.Column;
import javax.persistence.Id;

@javax.persistence.Entity
public class Sexo implements Entity {

	@Id
	@Column(name = "SEXO_ID")
	private Long id;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public String toString() {
		return descripcion;
	}
	
}
