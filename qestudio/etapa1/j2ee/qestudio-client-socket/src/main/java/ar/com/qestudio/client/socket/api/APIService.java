package ar.com.qestudio.client.socket.api;

import java.util.List;

import ar.com.qestudio.core.model.Attached;
import ar.com.qestudio.core.model.Registry;

public interface APIService {

	//Registry
	List<Registry> findAllRegistries();
	Registry findRegistry(int id);
	
	//Attached
	void openFile(Registry registry, Attached attached);
}