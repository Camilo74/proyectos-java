package ar.com.qestudio.server.dao;

import java.util.List;

import ar.com.qestudio.core.model.Registry;

public interface RegistryDAO {
	Registry create(Registry registry);
	Registry find(int id);
	List<Registry> findAll();
}