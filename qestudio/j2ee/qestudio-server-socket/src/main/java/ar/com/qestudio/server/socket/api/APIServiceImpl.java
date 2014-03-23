package ar.com.qestudio.server.socket.api;

import java.io.File;
import java.util.List;

import ar.com.qestudio.core.model.Attached;
import ar.com.qestudio.core.model.Registry;
import ar.com.qestudio.server.bo.RegistryBO;

public class APIServiceImpl implements APIService {

	//declaro el Bo
	private RegistryBO registryBO;
	
	@Override
	public List<Registry> findAllRegistries() {
		return registryBO.findAll();
	}

	@Override
	public Registry findRegistry(int id) {
		return registryBO.find(id);
	}

	@Override
	public File getFile(Registry registry, Attached attached) {
		return null;
	}
	
	//-----------------------------
	
	public RegistryBO getRegistryBO() {
		return registryBO;
	}

	public void setRegistryBO(RegistryBO registryBO) {
		this.registryBO = registryBO;
	}

}