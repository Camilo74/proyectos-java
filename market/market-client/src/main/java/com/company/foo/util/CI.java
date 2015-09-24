package com.company.foo.util;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;

public class CI {
	
	public static Object n(String classname) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return Class.forName(classname).newInstance();
	}
	
	public static Object n(String classname, Object ... params) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?>[] listClazz = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			listClazz[i] = params[i].getClass();
		}
		return Class.forName(classname).getConstructor(listClazz).newInstance(params);
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println(CI.n(SimpleDateFormat.class.getCanonicalName(),"yyyy-MM-dd'T'HH:mm"));
	}
}
