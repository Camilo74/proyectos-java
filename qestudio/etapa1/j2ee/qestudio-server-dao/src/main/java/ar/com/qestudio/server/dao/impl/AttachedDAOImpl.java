package ar.com.qestudio.server.dao.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import ar.com.qestudio.core.model.Attached;
import ar.com.qestudio.core.model.Interested;
import ar.com.qestudio.server.dao.AttachedDAO;

public class AttachedDAOImpl implements AttachedDAO {

	private final static Logger logger = Logger.getLogger(AttachedDAOImpl.class);
	private Properties prop;
	
	public AttachedDAOImpl(){
		try {
			prop = new Properties();
			InputStream in = getClass().getResourceAsStream("/conf.properties");
			prop.load(in);
			in.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public List<Attached> find(Interested interested, Integer registryId) {
		List<Attached> results = new ArrayList<Attached>();
		File[] files = new File(prop.getProperty("storage.file.path") + File.separator + interested.getName() + File.separator + registryId).listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		    	Attached at = new Attached();
		    	at.setId(1);
		    	at.setName(file.getName());
		    	at.setFullPath(file.getAbsolutePath());
		        results.add(at);
		    }
		}
		return results;
	}
	
	public static void main(String[] args) {
		AttachedDAOImpl dao = new AttachedDAOImpl();
		Interested interested = new Interested();
		interested.setName("Empresa1");
		List<Attached> result = dao.find(interested, 1);
		for (Attached attached : result) {
			System.out.println(attached.getName());
		}
	}

}