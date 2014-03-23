package ar.com.qestudio.server.bo.impl;

import java.util.List;

import ar.com.qestudio.core.model.Attached;
import ar.com.qestudio.core.model.Interested;
import ar.com.qestudio.server.bo.AttachedBO;
import ar.com.qestudio.server.dao.AttachedDAO;

public class AttachedBOImpl implements AttachedBO {

	private AttachedDAO dao;

	@Override
	public List<Attached> find(Interested interested, Integer registryId) {
		return dao.find(interested, registryId);
	}

	public AttachedDAO getDao() {
		return dao;
	}

	public void setDao(AttachedDAO dao) {
		this.dao = dao;
	}
	
}