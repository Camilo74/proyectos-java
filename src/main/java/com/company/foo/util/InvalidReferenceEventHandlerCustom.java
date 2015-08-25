package com.company.foo.util;

import org.apache.velocity.app.event.InvalidReferenceEventHandler;
import org.apache.velocity.context.Context;
import org.apache.velocity.util.introspection.Info;


public class InvalidReferenceEventHandlerCustom implements InvalidReferenceEventHandler{

	@Override
	public Object invalidGetMethod(Context arg0, String arg1, Object arg2,String arg3, Info arg4) {
		return "";
	}

	@Override
	public Object invalidMethod(Context arg0, String arg1, Object arg2,String arg3, Info arg4) {
		return "";
	}

	@Override
	public boolean invalidSetMethod(Context arg0, String arg1, String arg2, Info arg3) {
		return false;
	}

}
