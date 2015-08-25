package com.company.foo.util;

import java.util.Map;

public class Response {

	private Boolean ok;
	private Long id;
	private Object value;
	private String controller;
	private String action;
	private Map<String,Object> params;
	private String message;
		
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
	
	public Response controller(String controller){
		this.controller = controller;
		return this;
	}
	
	public Response action(String action){
		this.action = action;
		return this;
	}
	
	public Response params(Map<String,Object> params){
		this.params = params;
		return this;
	}

	public Response message(String message){
		this.message = message;
		return this;
	}

	public Response redirect(String controller, String action, Long id, Map<String,Object> params){
		this.id = id;
		this.controller = controller;
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

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
	
	public Boolean isMessage(){
		return message != null;
	}
	
	public String buildURL(){
		return "/" + controller + "/" + action + "/" + id;
	}
	
}
