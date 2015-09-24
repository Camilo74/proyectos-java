package com.company.foo.ws;

import java.util.List;

import com.company.foo.bo.BO;
import com.company.foo.bo.DefaultBO;
import com.company.foo.model.Entity;
import com.company.foo.util.ClassFinder;

public class DefaultWebService implements WebService {

	private BO bo;
	
	public DefaultWebService(Class<?> clazz){
		try {
			bo = (BO) Class.forName(ClassFinder.PACK_FULL_PATH + ClassFinder.PACK_BO_TYPE + "." + clazz.getSimpleName() + "WebService").newInstance();
		} catch (Exception e) {
			bo = new DefaultBO(clazz);
		}
	}
	
	@Override
	public List<?> list(Class<?> clazz, Long id, Entity entity){
		System.out.println("index ["+this.getClass().getSimpleName()+"]");
		return bo.list(clazz);
	}

	@Override
	public Entity get(Class<?> clazz, Long id, Entity entity){
		System.out.println("show ["+this.getClass().getSimpleName()+"] - id:" + id);
		return bo.get(clazz,id);
	}
	
	@Override
	public boolean save(Class<?> clazz, Long id, Entity entity){
		System.out.println("save ["+this.getClass().getSimpleName()+"] - entity:" + entity);
		return bo.save(entity);
	}
	
	@Override
	public boolean remove(Class<?> clazz, Long id, Entity entity){
		System.out.println("remove ["+this.getClass().getSimpleName()+"] - id:" + id);
		return bo.remove(clazz,id);
	}

}