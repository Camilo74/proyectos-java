package com.company.foo.dao;

import java.util.List;

import com.company.foo.model.Entity;

public interface DAO {
	List<?> list(Class<?> clazz);
	Object get(Class<?> clazz, Long id);
	Boolean remove(Class<?> clazz, Long id);
	void add(Entity entity);
	boolean save(Entity entity);
	boolean update(Entity entity);
}