package com.company.foo.model;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

@javax.persistence.Entity
public class Usuario implements Entity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "USUARIO_ID")
	private Long id;
	
	@Column(name = "CODIGO")
	private Long codigo;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "FECHA_ALTA")
	@Type(type="timestamp")
	private Date fechaDeAlta;	
	
	@Column(name = "SEXO")
	private Character sexo;

	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="USUARIO_ID")
	private List<Categoria> categorias;
	
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

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaDeAlta() {
		return fechaDeAlta;
	}

	public String getFechaDeAltaAsString() {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(fechaDeAlta);
	}
	
	public void setFechaDeAlta(Date fechaDeAlta) {
		this.fechaDeAlta = fechaDeAlta;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	@Override
	public String toString() {
		return id + "- " + nombre;
	}
	
}
