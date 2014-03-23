package ar.com.qestudio.server.bo;

import java.util.List;

import ar.com.qestudio.core.model.Registry;

public interface RegistryBO {
	Registry create(Registry registry);
	Registry find(int id);
	List<Registry> findAll();
}