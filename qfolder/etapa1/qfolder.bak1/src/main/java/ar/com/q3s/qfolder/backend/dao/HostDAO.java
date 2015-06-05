package ar.com.q3s.qfolder.backend.dao;


public interface HostDAO {

	void add(String host) throws Exception;
	String[] getAll() throws Exception;
	
}