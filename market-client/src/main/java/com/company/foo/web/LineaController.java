package com.company.foo.web;

import java.util.Map;

import com.company.foo.bo.BO;
import com.company.foo.bo.DefaultBO;
import com.company.foo.model.Entity;
import com.company.foo.model.Linea;
import com.company.foo.util.ClassFinder;
import com.company.foo.util.Response;

public class LineaController implements Controller {

	private BO bo;
	
	public LineaController(){}
	
	public LineaController(Class<?> clazz){
		try {
			bo = (BO) Class.forName(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_BO_TYPE + "." + clazz.getSimpleName() + "BO").newInstance();
		} catch (Exception e) {
			bo = new DefaultBO(clazz);
		}
	}
	
	@Override
	public Response index(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("index - params:" + params);
		return Response.ok(bo.list(clazz));
	}

	@Override
	public Response show(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("show - params:" + params);
		return Response.ok().message("No existe el codigo");
	}

	@Override
	public Response edit(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("edit - params:" + params);
		return Response.ok(bo.get(clazz,id));
	}

	@Override
	public Response create(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("create - params:" + params);
		try {
			return Response.ok(clazz.newInstance());
		} catch (Exception e) {
			return Response.fail();
		}
	}
	
	@Override
	public Response remove(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("remove - params:" + params);
		bo.remove(clazz,id);
		return Response.ok().redirect(clazz.getSimpleName(),"show", id, params).message("Eliminado correctamente");
	}
	
	@Override
	public Response save(Entity entity, Map<String,Object> params){
		System.out.println("save - params:" + params);
		bo.save(entity);
		return Response.ok().redirect(entity.getClass().getSimpleName(),"show", entity.getId(), params).message("Creado exitosamente");
	}

	@Override
	public Response update(Entity entity, Map<String,Object> params){
		System.out.println("update - params:" + params);
		bo.update(entity);
		return Response.ok().redirect(entity.getClass().getSimpleName(),"show", entity.getId(), params).message("Modificado exitosamente");
	}
	
}