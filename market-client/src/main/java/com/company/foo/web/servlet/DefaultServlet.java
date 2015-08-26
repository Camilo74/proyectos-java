package com.company.foo.web.servlet;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.EventCartridge;

import com.company.foo.bo.DefaultBO;
import com.company.foo.model.Entity;
import com.company.foo.util.CI;
import com.company.foo.util.ClassFinder;
import com.company.foo.util.InvalidReferenceEventHandlerCustom;
import com.company.foo.util.MessageUtils;
import com.company.foo.util.Response;
import com.company.foo.web.Controller;
import com.company.foo.web.DefaultController;

public class DefaultServlet extends HttpServlet {
	
	private static final long serialVersionUID = -3324097373661605976L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		//get context URI
    		Map<String,String> contextMap = getContextPath(request.getPathInfo().split("/"));
    		String controllerParam = contextMap.get("controller");
    		String actionParam = contextMap.get("action");
    		String contextParam = request.getServletPath();
    		Long idParam = contextMap.get("id") == null ? null : Long.valueOf(contextMap.get("id"));
    		Class<?> clazz = Class.forName(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_MODEL_TYPE + "." + controllerParam);
    		System.out.println("############### [GET] url: " + request.getPathInfo() + " - id:"+ idParam);
    		//--------------------------------------------------------------
        	Controller controller = null;
        	try {
				controller = (Controller) Class.forName(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_CONTROLLER_TYPE + "." + controllerParam + "Controller").newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				controller = new DefaultController(clazz);
			}

        	Map<String, Object> params = new HashMap<String,Object>();
        	for(Entry<String,String[]> val : request.getParameterMap().entrySet()){
        		params.put(val.getKey(), val.getValue()[0]);
        	}
        	
        	Method method = controller.getClass().getMethod(actionParam,Class.class,Long.class,Map.class);
        	Response result = (Response) method.invoke(controller,clazz,idParam,params);
        	
        	HttpSession session = request.getSession(true);
        	Response throwResponse = (Response) session.getAttribute("throw_response");
        	if(throwResponse != null){
        		result.setId(throwResponse.getId());
        		result.setMessage(throwResponse.getMessage());
        		result.setParams(throwResponse.getParams());
        		session.removeAttribute("throw_response");
        	}
        	
    		/*  create a context and add data */
    		VelocityContext context = new VelocityContext();
    		
	    	params.put("id", result.getId());
	    	params.put("controller", result.getController() != null ? result.getController() : controllerParam);
	    	params.put("action", result.getAction() != null ? result.getAction() : actionParam);
	    	params.put("context", contextParam);
	    	params.put("entities", ClassFinder.find(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_MODEL_TYPE));
        	
	    	context.put("params", result.getParams());
        	context.put("instance", result);
        	context.put("class",clazz);
        	context.put("controller",controller);
        	context.put("classFinder",ClassFinder.class);
        	context.put("ci",CI.class);
        	context.put("filter",clazz.newInstance());
        	
        	MessageUtils message = new MessageUtils(params.get("controller").toString(), params.get("action").toString());
        	context.put("message", message);
        	
        	/*  first, get and initialize an engine  */
	        VelocityEngine ve = new VelocityEngine();
	        EventCartridge ec = new EventCartridge();
	        ec.addEventHandler(new InvalidReferenceEventHandlerCustom());
	        ec.attachToContext( context );
	        ve.init();
	        /*  next, get the Template  */
	        Template t = null;
	        try {
	        	t = ve.getTemplate( "src/main/webapp/view/" + params.get("controller") + "/" + params.get("action") + ".html" );
			} catch (Exception e) {
				t = ve.getTemplate( "src/main/webapp/view/default/" + params.get("action") + ".html" );
			}
        	
	        /* now render the template into a StringWriter */
	        StringWriter writer = new StringWriter();
	        t.merge( context, writer );
	        /* show the World */
	        response.getWriter().print(writer.toString());
	        
		} catch (Exception e) {
			super.doGet(request, response);
		}    	
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		try {

			//get context URI
			Map<String,String> contextMap = getContextPath(request.getPathInfo().split("/"));
			String controllerParam = contextMap.get("controller");
			String actionParam = contextMap.get("action");
			String contextParam = request.getServletPath();
			Long idParam = contextMap.get("id") == null ? null : Long.valueOf(contextMap.get("id"));
			Class<?> clazz = Class.forName(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_MODEL_TYPE + "." + controllerParam);
			System.out.println("############### [POST] url: " + request.getPathInfo() + " - id:"+ idParam);
			//--------------------------------------------------------------
	    	Controller controller = null;
	    	try {
				controller = (Controller) Class.forName(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_CONTROLLER_TYPE + "." + controllerParam + "Controller").newInstance();
			} catch (Exception e) {
				controller = new DefaultController(clazz);
			}
	
	    	Map<String, Object> params = new HashMap<String,Object>();
	    	for(Entry<String,String[]> val : request.getParameterMap().entrySet()){
	    		params.put(val.getKey(), val.getValue()[0]);
	    	}
	    	
	    	Method method = controller.getClass().getMethod(actionParam,Entity.class,Map.class);
	    	Response result = (Response) method.invoke(controller,createInstance(clazz,params),params);
	    	HttpSession session = request.getSession(true);
	    	session.setAttribute("throw_response", result);
	    	response.sendRedirect(contextParam + result.buildURL());
	    	
		} catch (Exception e) {
			e.printStackTrace();
			super.doPost(request, response);
		}
    	
	}
	
	private Entity createInstance(Class<?> clazz, Map<String,Object> params){
		try {
			Object instance = clazz.newInstance();
			for (Entry<String,Object> element : params.entrySet()) {
				try {
					if(element.getValue() != null && !element.getValue().toString().isEmpty()){
						String value = element.getKey().toString();
						if( value.indexOf(".") == -1){
							Field field = clazz.getDeclaredField(element.getKey());
							field.setAccessible(true);
							if(field.getType().getSimpleName().equals("Boolean")){
								field.set(instance, field.getType().getConstructor(new Class[]{String.class}).newInstance("on".equals(element.getValue()) ? "true" : "false"));
							}else if(field.getType().getSimpleName().equals("Date")){
								SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
								Date date = formatter.parse(element.getValue().toString());
								field.set(instance, date);//2015-01-01T01:00
							}else{
								field.set(instance, field.getType().getConstructor(new Class[]{String.class}).newInstance(element.getValue()));								
							}
						}else{
							Field field = clazz.getDeclaredField(value.split("\\.")[0]);
							field.setAccessible(true);
							if(field.getType().getSimpleName().equals("List")){
								Class<?> classType = (Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
								Entity entity = (Entity) new DefaultBO(classType).get(classType, new Long(element.getValue().toString()));
								if(field.get(instance) == null){
									field.set(instance, new ArrayList());
								}
								((List)field.get(instance)).add(entity);
							}else if(ClassFinder.exist(field.getType())){
								field.set(instance,new DefaultBO(field.getClass()).get(field.getType(), new Long(element.getValue().toString())));
							}
						}
						
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return (Entity)instance;
		} catch (Exception e) {
			return null;
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