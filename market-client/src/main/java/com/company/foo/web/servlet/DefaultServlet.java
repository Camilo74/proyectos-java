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

import javax.persistence.GeneratedValue;
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
	private static final String REDIRECT_MESSAGE_KEY = "messages";

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	try {
    		//get context URI
    		Map<String,String> contextMap = getContextPath(req.getPathInfo().split("/"));
    		String entityParam = contextMap.get("entity");
    		String actionParam = contextMap.get("action");
    		String contextParam = req.getServletPath();
    		Long idParam = contextMap.get("id") == null ? null : Long.valueOf(contextMap.get("id"));
    		//--------------------------------------------------------------
    		Class<?> clazz = Class.forName(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_MODEL_TYPE + "." + entityParam);
    		//--------------------------------------------------------------
    		System.out.println("############### [GET] url: " + req.getPathInfo() + " - id:"+ idParam);
    		//--------------------------------------------------------------
        	Controller controller = null;
        	try {
				controller = (Controller) Class.forName(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_CONTROLLER_TYPE + "." + entityParam + "Controller").newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				controller = new DefaultController(clazz);
			}
        	//--------------------------------------------------------------
        	Map<String, Object> params = new HashMap<String,Object>();
        	//--------------------------------------------------------------
        	for(Entry<String,String[]> val : req.getParameterMap().entrySet()){
        		params.put(val.getKey(), val.getValue()[0]);
        	}
        	//--------------------------------------------------------------
        	Method method = controller.getClass().getMethod(actionParam,Class.class,Long.class,Map.class);
        	Response response = (Response) method.invoke(controller,clazz,idParam,params);
        	//--------------------------------------------------------------
			params.putAll(response.getParams());
        	//--------------------------------------------------------------
			params.put("id", response.getId() != null ? response.getId() : idParam);
    		params.put("entity", response.getEntity() != null ? response.getEntity() : entityParam);
    		params.put("action", response.getAction() != null ? response.getAction() : actionParam);
        	params.put("context", contextParam);
        	params.put("entities", ClassFinder.find(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_MODEL_TYPE));
        	params.put("clazz",clazz);
        	//--------------------------------------------------------------
        	HttpSession session = req.getSession(true);
        	Object rmessages = session.getAttribute(REDIRECT_MESSAGE_KEY);
        	if(rmessages != null){
        		List<String> messages = (List<String>)rmessages;
        		for(String msg : messages){
        			response.addMessage(msg);        			
        		}
        		session.removeAttribute(REDIRECT_MESSAGE_KEY);
        	}
        	//--------------------------------------------------------------
    		VelocityContext context = new VelocityContext();
    		params.put("instance", response);
        	context.put("params", params);
        	context.put("ci",CI.class);
        	context.put("controller",controller);
        	context.put("classFinder",ClassFinder.class);
        	context.put("message", new MessageUtils(params.get("entity").toString(), params.get("action").toString()));
        	//--------------------------------------------------------------
            VelocityEngine ve = new VelocityEngine();
            EventCartridge ec = new EventCartridge();
            ec.addEventHandler(new InvalidReferenceEventHandlerCustom());
            ec.attachToContext(context);
            ve.init();
            //--------------------------------------------------------------
            Template template = null;
            try {
            	template = ve.getTemplate( "src/main/webapp/view/" + params.get("entity") + "/" + params.get("action") + ".html" );
    		} catch (Exception e) {
    			template = ve.getTemplate( "src/main/webapp/view/default/" + params.get("action") + ".html" );
    		}
            //--------------------------------------------------------------
            StringWriter writer = new StringWriter();
            template.merge( context, writer );
            //--------------------------------------------------------------
	        resp.getWriter().print(writer.toString());
		} catch (Exception e) {
			e.printStackTrace();
			super.doGet(req, resp);
		}    	
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		try {
			//post context URI
    		Map<String,String> contextMap = getContextPath(req.getPathInfo().split("/"));
    		String entityParam = contextMap.get("entity");
    		String actionParam = contextMap.get("action");
    		String contextParam = req.getServletPath();
    		Long idParam = contextMap.get("id") == null ? null : Long.valueOf(contextMap.get("id"));
    		//--------------------------------------------------------------
    		Class<?> clazz = Class.forName(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_MODEL_TYPE + "." + entityParam);
    		//--------------------------------------------------------------
    		System.out.println("############### [POST] url: " + req.getPathInfo() + " - id:"+ idParam);
    		//--------------------------------------------------------------
        	Controller controller = null;
        	try {
				controller = (Controller) Class.forName(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_CONTROLLER_TYPE + "." + entityParam + "Controller").newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				controller = new DefaultController(clazz);
			}
        	//--------------------------------------------------------------
        	Map<String, Object> params = new HashMap<String,Object>();
        	//--------------------------------------------------------------
        	for(Entry<String,String[]> val : req.getParameterMap().entrySet()){
        		params.put(val.getKey(), val.getValue()[0]);
        	}
        	//--------------------------------------------------------------
	    	Method method = controller.getClass().getMethod(actionParam,Entity.class,Map.class);
	    	Response response = (Response) method.invoke(controller,createInstance(clazz,params),params);
	    	//--------------------------------------------------------------
			params.put("id", response.getId() != null ? response.getId() : idParam);
    		params.put("entity", response.getEntity() != null ? response.getEntity() : entityParam);
    		params.put("action", response.getAction() != null ? response.getAction() : actionParam);
    		//--------------------------------------------------------------
	    	HttpSession session = req.getSession(true);
	    	session.setAttribute(REDIRECT_MESSAGE_KEY, response.getMessages());
	    	resp.sendRedirect(contextParam + "/" + params.get("entity") + "/" + params.get("action") + "/" + params.get("id"));
		} catch (Exception e) {
			e.printStackTrace();
			super.doPost(req, resp);
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
							}else if(field.getType().getSimpleName().equals("Character")){
								field.set(instance,element.getValue().toString().toCharArray()[0]);
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
		for (String item : new String[]{"entity","action","id"}) {
			try {
				String url = urlPathInfo[cdor++];
				System.out.println("### urlPathInfo[cdor++]" + url); 
				map.put(item, url);
			} catch (Exception e) {
				map.put(item, null);
			}
		}
		//map.put("last", urlPathInfo[urlPathInfo.length-1]);
		return map;
	}
	
}