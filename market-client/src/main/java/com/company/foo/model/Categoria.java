package com.company.foo.model;

import javax.persistence.Column;
import javax.persistence.Id;

@javax.persistence.Entity
public class Categoria implements Entity{

	@Id
	@Column(name = "CATEGORIA_ID")
	private Long id;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
}
