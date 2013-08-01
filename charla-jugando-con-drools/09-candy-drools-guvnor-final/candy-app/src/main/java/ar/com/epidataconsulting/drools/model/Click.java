package ar.com.epidataconsulting.drools.model;

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
