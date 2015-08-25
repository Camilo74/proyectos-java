package com.company.foo.util;

import java.text.MessageFormat;
import java.util.Properties;

public class MessageUtils {

	private String controller;
	private String action;
	private Properties prop = new Properties();
	
	public MessageUtils() {
		try {
			prop.load(MessageUtils.class.getClassLoader().getResourceAsStream("messages.properties"));			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MessageUtils(String controller, String action) {
		this();
		this.controller = controller;
		this.action = action;
	}
	
	public String get(String key){
		return ev(getFullKey(key), null);
	}
	
	public String get(String key, String args){
		return ev(getFullKey(key),args);
	}
	
	private String ev(String key, String args){
		Object value = prop.get(key);
		if(value != null){
			return MessageFormat.format(value.toString(),args);
		}else{
			if(key.indexOf(".") == -1){
				return key;
			}else{
				return ev(key.substring(key.indexOf(".")+1),args);
			}
		}
	}
	
	private String getFullKey(String key){
		String p0 = (controller == null ? "" : "{0}");
		String p1 = (action == null ? "" : p0.isEmpty() ? "{1}" : ".{1}");
		String p2 = p1.isEmpty() ? "{2}" : ".{2}";
		return MessageFormat.format(p0+p1+p2, controller, action, key);
	}
	
	public static void main(String[] args) {
		System.out.println(new MessageUtils("User","index").get("true"));
	}
	
}