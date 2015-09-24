package com.company.foo.dao;

import java.util.List;

import com.company.foo.model.Entity;

public interface DAO {
	List<?> list(Class<?> clazz);
	Entity get(Class<?> clazz, Long id);
	boolean persist(Entity entity);
	boolean merge(Entity entity);
	boolean remove(Class<?> clazz, Long id);
}