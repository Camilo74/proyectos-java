package com.company.foo.web;

import java.util.List;
import java.util.Map;

import com.company.foo.bo.BO;
import com.company.foo.bo.DefaultBO;
import com.company.foo.model.Entity;
import com.company.foo.model.Usuario;
import com.company.foo.util.Response;

public class UsuarioController implements Controller {

	private BO bo;
	
	public UsuarioController(){
		bo = new DefaultBO(Usuario.class);
	}
	
	@Override
	public Response index(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("index - params:" + params);
		return Response.ok(bo.list(clazz));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response show(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("show - params:" + params);
		
		List<Usuario> usuarios = (List<Usuario>) bo.list(Usuario.class);
		
		Usuario us = null;
		for (Usuario usuario : usuarios) {
			if(usuario.getCodigo().equals(Long.valueOf((String)params.get("code")))){
				us = usuario;
				break;
			}
		}
		
		if(us == null){
			us = new Usuario();
			us.setCodigo(Long.valueOf((String)params.get("code")));
			return Response.fail(us).redirect(clazz.getSimpleName(),"create", id, params).message("No existe el usuario registrado");
		}else{
			return Response.ok(us).redirect(clazz.getSimpleName(),"show", id, params).message("Usuario registrado");
		}
		
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