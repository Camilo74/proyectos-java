package com.company.foo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@javax.persistence.Entity
public class Usuario implements Entity{

	@Id
	@Column(name = "USUARIO_ID")
	private Long id;
	
	@Column(name = "CODIGO")
	private Long codigo;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "FECHA_ALTA")
	private String fechaDeAlta;	
	
	@Column(name = "HABILITADO")
	private Boolean habilitado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SEXO_ID", nullable = false)
	private Sexo sexo;

	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="USUARIO_ID")
	private List<Categoria> categoria;
	
	@Override
	public Long getId() {
		return id;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFechaDeAlta() {
		return fechaDeAlta;
	}

	public void setFechaDeAlta(String fechaDeAlta) {
		this.fechaDeAlta = fechaDeAlta;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public List<Categoria> getCategoria() {
		return categoria;
	}

	public void setCategoria(List<Categoria> categoria) {
		this.categoria = categoria;
	}
	
	@Override
	public String toString() {
		return id + "- " + nombre;
	}
	
}
