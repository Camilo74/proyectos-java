package ar.com.qestudio.server.backend.bo;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import ar.com.qestudio.server.backend.dao.ListFileDAO;

public class ListFileBOImpl implements ListFileBO {

	private ListFileDAO dao;
	
	@Override
	public Set<String> getAll() {
		Set<String> list = new TreeSet<String>();
		for (File file : dao.getAll()) {
			list.add(file.getName());
		}
		return list;
	}

	public ListFileDAO getDao() {
		return dao;
	}

	public void setDao(ListFileDAO dao) {
		this.dao = dao;
	}

}
