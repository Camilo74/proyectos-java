package ar.com.qestudio.core.model;

import java.io.Serializable;

public class Attached implements Serializable {

	private static final long serialVersionUID = 1732487395456918369L;
	
	private Integer id;
	private String name;
	private String fullPath;
	private String fullPathTemp;
	
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

	@Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Attached) {
        	Attached attached = (Attached) obj;
            return (attached.getId().equals(this.getId()));
        }
        return false;
    }
	
	@Override
	public int hashCode(){
		return 0;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public String getFullPathTemp() {
		return fullPathTemp;
	}
	public void setFullPathTemp(String fullPathTemp) {
		this.fullPathTemp = fullPathTemp;
	}
	
}