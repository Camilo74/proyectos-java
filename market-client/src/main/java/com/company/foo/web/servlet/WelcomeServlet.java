package com.company.foo.web.servlet;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.company.foo.util.ClassFinder;

public class WelcomeServlet extends HttpServlet {
	
	private static final long serialVersionUID = -3324097373661605976L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$");
    		//get context URI
    		String contextParam = request.getServletPath();
    		//--------------------------------------------------------------
	    	Map<String, Object> params = new HashMap<String,Object>();
	    	params.put("context", contextParam);
	    	params.put("entities", ClassFinder.find(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_MODEL_TYPE));
	    	
	        /*  first, get and initialize an engine  */
	        VelocityEngine ve = new VelocityEngine();
	        ve.init();
	        /*  next, get the Template  */
	        VelocityContext context = new VelocityContext();
	        Template t = ve.getTemplate( "src/main/webapp/view/index.html" );
        	/*  create a context and add data */
        	context.put("params", params);
	        /* now render the template into a StringWriter */
	        StringWriter writer = new StringWriter();
	        t.merge( context, writer );
	        /* show the World */
	        response.getWriter().print(writer.toString());
		} catch (Exception e) {
			super.doGet(request, response);
		}    	
    }

	private Map<String, String> getContextPath(String[] urlPathInfo) {
		int cdor = 1;
		Map<String,String> map = new HashMap<String,String>();
		for (String item : new String[]{"controller","action","id"}) {
			try {
				map.put(item, urlPathInfo[cdor++]);
			} catch (Exception e) {
				map.put(item, null);
			}
		}
		map.put("last", urlPathInfo[urlPathInfo.length-1]);
		return map;
	}
	
}