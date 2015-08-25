package com.company.foo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@javax.persistence.Entity
public class Garage implements Entity{
	
	@Id
	@Column(name = "GARAGE_ID")
	private Long id;
	private String ip;
	private String nombre;
	private Date fecha;
	
	@OneToMany(mappedBy="garage")
	private List<Empresa> empresas;

	public Garage(Long id, String ip, String nombre, List<Empresa> empresas) {
		super();
		this.id = id;
		this.ip = ip;
		this.nombre = nombre;
		this.empresas = empresas;
	}

	public Garage(){
	}
	
	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setVehiculos(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
