package com.company.foo.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@javax.persistence.Entity
public class Linea implements Entity {

	@Id
	@Column(name = "LINEA_ID")
	private Long id;
	
	@Column(name = "numero")
	private Integer numero;
	
	@ManyToOne
	private Empresa empresa;
	
	public Linea(){}
	
	public Linea(Long id, Integer numero) {
		super();
		this.id = id;
		this.numero = numero;
	}	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return numero.toString();
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Linea other = (Linea) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}