package com.company.foo.web;

import java.util.Map;

import com.company.foo.model.Entity;
import com.company.foo.util.Response;

public interface Controller {
	Response index(Class<?> clazz, Long id, Map<String,Object> params);
	Response show(Class<?> clazz, Long id, Map<String,Object> params);
	Response edit(Class<?> clazz, Long id, Map<String,Object> params);
	Response create(Class<?> clazz, Long id, Map<String,Object> params);
	Response remove(Class<?> clazz, Long id, Map<String,Object> params);
	Response save(Entity entity, Map<String, Object> params);
	Response update(Entity entity, Map<String, Object> params);
}