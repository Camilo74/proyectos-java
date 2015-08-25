package com.company.foo.bo;

import java.util.List;

import com.company.foo.model.Entity;

public interface BO {

	List<?> list(Class<?> clazz);
	Object get(Class<?> clazz, Long id);
	Object remove(Class<?> clazz, Long id);
	boolean save(Entity entity);
	boolean update(Entity entity);

}