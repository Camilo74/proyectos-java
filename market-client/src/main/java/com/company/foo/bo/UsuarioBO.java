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

	public Usuario getByCode(Class<?> clazz,Long codigo){
		List<Usuario> usuarios = (List<Usuario>) dao.list(Usuario.class);
		for (Usuario usuario : usuarios) {
			if(codigo.equals(usuario.getCodigo())){
				return usuario;
			}
		}
		return null;
	}
	
	@Override
	public List<?> list(Class<?> clazz){
		return dao.list(clazz);
	}

	@Override
	public Object get(Class<?> clazz, Long id) {
		return dao.get(clazz,id);
	}

	@Override
	public Object remove(Class<?> clazz, Long id) {
		return dao.remove(clazz,id);
	}

	@Override
	public boolean save(Entity entity) {
		return dao.save(entity);
	}

	@Override
	public boolean update(Entity entity) {
		return dao.update(entity);
	}
	
}