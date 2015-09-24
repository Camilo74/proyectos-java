package com.company.foo.bo;

import java.util.List;

import com.company.foo.dao.DAO;
import com.company.foo.dao.DefaultDAO;
import com.company.foo.model.Entity;
import com.company.foo.model.Usuario;

public class UsuarioBO implements BO {

	private DAO dao;
	
	public UsuarioBO(){
		dao = new DefaultDAO();
	}
	
	@Override
	public List<?> list(Class<?> clazz){
		return dao.list(clazz);
	}

	@Override
	public Entity get(Class<?> clazz, Long id) {
		return dao.get(clazz,id);
	}

	@Override
	public boolean save(Entity entity) {
		if(entity.getId() == null){
			return dao.persist(entity);
		}else{
			return dao.merge(entity);
		}
	}
	
	@Override
	public boolean remove(Class<?> clazz, Long id) {
		return dao.remove(clazz,id);
	}

	@SuppressWarnings("unchecked")
	public Usuario findByCode(Class<?> clazz, Long codigo){
		List<Usuario> usuarios = (List<Usuario>) dao.list(Usuario.class);
		for (Usuario usuario : usuarios) {
			if(codigo.equals(usuario.getCodigo())){
				return usuario;
			}
		}
		return null;
	}

}