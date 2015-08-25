package com.company.foo.bo;

import java.util.List;

import com.company.foo.dao.DAO;
import com.company.foo.dao.DefaultDAO;
import com.company.foo.model.Entity;
import com.company.foo.util.ClassFinder;

public class DefaultBO implements BO {

	private DAO dao;
	
	public DefaultBO(Class<?> clazz){
    	try {
			dao = (DAO) Class.forName(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_DAO_TYPE + "." + clazz.getSimpleName() + "DAO").newInstance();
		} catch (Exception e) {
			dao = new DefaultDAO();
		}		
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