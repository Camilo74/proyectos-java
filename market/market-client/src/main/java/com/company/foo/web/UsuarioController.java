package com.company.foo.web;

import java.util.Map;

import com.company.foo.bo.UsuarioBO;
import com.company.foo.model.Entity;
import com.company.foo.model.Usuario;
import com.company.foo.util.Response;

public class UsuarioController implements Controller {

	private UsuarioBO bo;
	
	public UsuarioController(){
		bo = new UsuarioBO();
	}
	
	@Override
	public Response index(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("index - params:" + params);
		return Response.ok(bo.list(clazz));
	}

	@Override
	public Response show(Class<?> clazz, Long id, Map<String,Object> params){
		System.out.println("show - params:" + params);
		Object scodigo = params.get("code");
			
		if(scodigo==null){
			if(id==null){
				return Response.fail().redirect(clazz.getSimpleName(),"index", id, params).message("Parametros mal definidos");
			}else{
				return Response.ok(bo.get(clazz,id));				
			}
		}else{
			Long lcodigo = Long.valueOf(scodigo.toString());
			Usuario usuario = (Usuario) bo.findByCode(Usuario.class,lcodigo);
			if(usuario==null){
				usuario = new Usuario();
				usuario.setCodigo(lcodigo);
				return Response.fail(usuario).redirect(clazz.getSimpleName(),"create", id, params).message("No existe el usuario registrado");
			}else{
				return Response.ok(usuario).message("Usuario registrado");
			}
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
		bo.save(entity);
		return Response.ok().redirect(entity.getClass().getSimpleName(),"show", entity.getId(), params).message("Modificado exitosamente");
	}
	
}