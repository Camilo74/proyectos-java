package ar.com.qestudio.core.model.mensaje.impl;

import ar.com.qestudio.core.model.Contacto;
import ar.com.qestudio.core.model.mensaje.IMessage;

public class Callback implements IMessage {

	private static final long serialVersionUID = -8093885687012524901L;

	private Long id;
	private Contacto contact; // origen
	private String functionName;
	private Object[] parameters;
	private Object value;

	public Callback() {
		id = System.currentTimeMillis();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Contacto getContact() {
		return contact;
	}

	public void setContact(Contacto contact) {
		this.contact = contact;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Callback) {
        	Callback contacto = (Callback) obj;
            return (contacto.getId().equals(this.getId()));
        }
        return false;
    }
	
	@Override
	public int hashCode(){
		return 0;
	}

}