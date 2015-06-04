package ar.com.q3s.qfolder.backend.dao;

import java.io.File;
import java.io.FileFilter;

import ar.com.q3s.qfolder.util.PropertyUtils;

public class ListFileDAOImpl implements ListFileDAO{

	@Override
	public File[] getAll() {
		String path = PropertyUtils.getProperty("qfolder.server.path.local");
		File file = new File(path);
		return file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		});		
	}

}
