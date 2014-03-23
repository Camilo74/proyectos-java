package ar.com.qestudio.server.socket.api;

import java.io.File;
import java.util.List;

import ar.com.qestudio.core.model.Attached;
import ar.com.qestudio.core.model.Registry;

public interface APIService {
	
	//Registry
	List<Registry> findAllRegistries();
	Registry findRegistry(int id);
	
	//File
	File getFile(Registry registry, Attached attached);
	
}