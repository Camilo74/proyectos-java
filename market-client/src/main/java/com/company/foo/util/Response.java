package com.company.foo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {

	private Boolean ok;
	private Long id;
	private Object value;
	private String entity;
	private String action;
	private Map<String,Object> params = new HashMap<String, Object>();
	private List<String> messages = new ArrayList<String>();
		
	public Response(Boolean ok){
		this.ok = ok;
	}

	public static Response ok(){
		return new Response(true);
	}
	
	public static Response ok(Object value){
		Response response = new Response(true);
		response.value = value;
		return response;
	}

	public static Response fail(){
		return new Response(false);
	}
	
	public static Response fail(Object value){
		Response response = new Response(false);
		response.value = value;
		return response;
	}
	
	public Response id(Long id){
		this.setId(id);
		return this;
	}
	
	public Response value(Object value){
		this.value = value;
		return this;
	}
	
	public Response entity(String entity){
		this.entity = entity;
		return this;
	}
	
	public Response action(String action){
		this.action = action;
		return this;
	}
	
//	public Response params(Map<String,Object> params){
//		this.params = params;
//		entity = params.get("entity").toString();
//		action = params.get("action").toString();
//		return this;
//	}

	public Response message(String message){
		addMessage(message);
		return this;
	}

	public Response redirect(String entity, String action, Long id, Map<String,Object> params){
		this.id = id;
		this.entity = entity;
		this.action = action;
		this.params = params;
		return this;
	}

	public Boolean isOk() {
		return ok;
	}
	
	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public List<String> getMessages() {
		return messages;
	}
	
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Boolean isRedirect(){
		return action != null;
	}
	
	public Boolean hasMessage(){
		return !messages.isEmpty();
	}

	public void addMessage(Object message){
		if(message != null){
			addMessage(message.toString());			
		}
	}
	
	public void addMessage(String message){
		if(message != null){
			messages.add(message);			
		}
	}
	
}
