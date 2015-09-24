package com.company.foo.bo;

import java.util.List;

import com.company.foo.model.Entity;

public interface BO {
	List<?> list(Class<?> clazz);
	Entity get(Class<?> clazz, Long id);
	boolean save(Entity entity);
	boolean remove(Class<?> clazz, Long id);
}