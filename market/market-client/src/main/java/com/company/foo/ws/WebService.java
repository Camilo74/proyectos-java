package com.company.foo.ws;

import java.util.List;

import com.company.foo.model.Entity;

public interface WebService {
	List<?> list(Class<?> clazz, Long id, Entity entity);
	Entity get(Class<?> clazz, Long id, Entity entity);
	boolean save(Class<?> clazz, Long id, Entity entity);
	boolean remove(Class<?> clazz, Long id, Entity entity);
}