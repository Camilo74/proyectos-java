package ar.com.q3s.qfolder.backend.bo;

import ar.com.q3s.qfolder.backend.dao.HostDAO;

public class HostBOImpl implements HostBO {

	private HostDAO dao;

	@Override
	public void add(String host) throws Exception {
		dao.add(host);
	}

	@Override
	public String[] getAll() throws Exception {
		return dao.getAll();
	}
	
	//----------------------------------

	public HostDAO getDao() {
		return dao;
	}

	public void setDao(HostDAO dao) {
		this.dao = dao;
	}

}