package ar.com.qestudio.server.bo.impl;

import java.util.List;

import ar.com.qestudio.core.model.Registry;
import ar.com.qestudio.server.bo.AttachedBO;
import ar.com.qestudio.server.bo.RegistryBO;
import ar.com.qestudio.server.dao.RegistryDAO;

public class RegistryBOImpl implements RegistryBO {

	private RegistryDAO registryDAO;
	private AttachedBO attachedBO;

	@Override
	public Registry create(Registry registry) {
		return registryDAO.create(registry);
	}

	@Override
	public Registry find(int id) {
		return registryDAO.find(1);
	}

	@Override
	public List<Registry> findAll() {
		List<Registry> results = registryDAO.findAll();
		for (Registry registry : results) {
			registry.setAttachments(attachedBO.find(registry.getInterested(), registry.getId()));
		}
		return results;
	}

	//-------------------------------------
	
	public RegistryDAO getRegistryDAO() {
		return registryDAO;
	}

	public void setRegistryDAO(RegistryDAO registryDAO) {
		this.registryDAO = registryDAO;
	}

	public AttachedBO getAttachedBO() {
		return attachedBO;
	}

	public void setAttachedBO(AttachedBO attachedBO) {
		this.attachedBO = attachedBO;
	}
	
}