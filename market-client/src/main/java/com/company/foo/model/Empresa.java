package com.company.foo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@javax.persistence.Entity
public class Empresa implements Entity {
	
	@Id
	@Column(name = "EMPRESA_ID")
	private Long id;
	private String nombre;
	private Boolean habilitado;
	
	@OneToMany(mappedBy="empresa")
	private List<Linea> lineas;
	
	@ManyToOne
	private Garage garage;
	
	public Empresa(){
	}
	
	public Empresa(Long id, String nombre, Boolean habilitado, List<Linea> lineas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.setHabilitado(habilitado);
		this.lineas = lineas;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public List<Linea> getLineas() {
		return lineas;
	}

	public void setLineas(List<Linea> lineas) {
		this.lineas = lineas;
	}

	@Override
	public String toString() {
		return nombre;
	}

	public Garage getGarage() {
		return garage;
	}

	public void setGarage(Garage garage) {
		this.garage = garage;
	}
	
}