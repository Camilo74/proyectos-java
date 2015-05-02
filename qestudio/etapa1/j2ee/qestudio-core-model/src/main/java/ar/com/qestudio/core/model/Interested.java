package ar.com.qestudio.core.model;

import java.io.Serializable;

public class Interested implements Serializable {
	
	private static final long serialVersionUID = -7327567129194404681L;
	private Integer id;
	private String name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}