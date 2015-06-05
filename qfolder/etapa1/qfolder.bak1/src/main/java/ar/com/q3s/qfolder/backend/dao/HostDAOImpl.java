package ar.com.q3s.qfolder.backend.dao;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HostDAOImpl implements HostDAO {

	private static final String FILENAME = "hosts.ini";

	@Override
	public void add(String host) throws Exception {
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			URL url = HostDAOImpl.class.getClassLoader().getResource(FILENAME);
			fw = new FileWriter(url.getPath(),true);
			pw = new PrintWriter(fw);
			pw.print(host + "\n");
			System.out.println("##### agregando host: " + host);
		} finally {
			fw.close();
		}
	}

	@Override
	public String[] getAll() throws Exception {
		List<String> list = new ArrayList<String>();
		DataInputStream in = null;
		try {
			in = new DataInputStream(HostDAOImpl.class.getClassLoader().getResourceAsStream(FILENAME));
			// Lectura del fichero
			String linea;
			while ((linea = in.readLine()) != null) {
				list.add(linea);
			}
		} finally {
			in.close();
		}
		return list.toArray(new String[] {});
	}

}