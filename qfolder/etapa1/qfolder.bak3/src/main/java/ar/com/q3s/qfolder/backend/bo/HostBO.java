package ar.com.q3s.qfolder.backend.bo;

public interface HostBO {

	void add(String host) throws Exception;
	String[] getAll() throws Exception;
	
}