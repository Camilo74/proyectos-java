package ar.com.qestudio.core.model;

import java.io.Serializable;

public class Contacto implements Serializable {

	private static final long serialVersionUID = -6783539509723025183L;
	
	private Boolean estado;
	private String nick;
	private String ip;

	public Contacto(){
		estado = true;
	}
	
	public Contacto(Boolean estado, String nombre, String ip) {
		this.estado = estado;
		this.nick = nombre;
		this.ip = ip;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Contacto) {
        	Contacto contacto = (Contacto) obj;
            return (contacto.getNick().equals(this.getNick()));
        }
        return false;
    }
	
	@Override
	public int hashCode(){
		return 0;
	}
}