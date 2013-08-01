package com.epidataconsulting.drools.model;

import java.io.Serializable;
import java.util.Date;

public class EventB implements Serializable {

	private static final long serialVersionUID = -5267624134171027993L;
	
	private String id;	
	private Date timestamp;
	private Long durationTime;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public Long getDurationTime() {
		return durationTime;
	}
	
	public void setDurationTime(Long duration) {
		this.durationTime = duration;
	}
}
