package ar.com.qestudio.server.backend.dao;

import java.io.File;
import java.io.FileFilter;

import ar.com.qestudio.server.backend.util.ContextHolderUtil;

public class ListFileDAOImpl implements ListFileDAO{

	@Override
	public File[] getAll() {
		String path = ContextHolderUtil.getProperty("qestudio.server.path.local");
		File file = new File(path);
		return file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile();
			}
		});
		
	}

}
