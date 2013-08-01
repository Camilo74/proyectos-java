package ar.com.epidataconsulting.drools;

import java.util.Date;

public class Click {

	private Date timestamp;
	
	public Click(){
		this.timestamp = new Date();
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}
