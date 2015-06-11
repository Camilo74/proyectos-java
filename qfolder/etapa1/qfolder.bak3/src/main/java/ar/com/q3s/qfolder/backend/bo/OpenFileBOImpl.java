package ar.com.q3s.qfolder.backend.bo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

import ar.com.q3s.qfolder.exception.CommandNotFoundException;
import ar.com.q3s.qfolder.util.PropertyUtils;

public class OpenFileBOImpl implements OpenFileBO {

	@Override
	public String open(String host, String filename) {
		try {
			String url = host + "/ws/get/" + filename;
			File temp = new File(System.getProperty("java.io.tmpdir") + File.separator + filename);
			System.out.println("##### open " + url);
			// --------------------
			Client client =  new ResteasyClientBuilder()
		    .establishConnectionTimeout(2, TimeUnit.SECONDS)
		    .socketTimeout(2, TimeUnit.SECONDS)
		    .build();
			
			WebTarget target = client.target(url);
			Response response = target.request().get();
			File remoteFile = response.readEntity(File.class);
			response.close(); // You should close connections!
			File dest = new File(temp.toURI().toURL().getPath());
			copyFileUsingStream(remoteFile, dest);
			open(dest);
			put(host,filename, dest);
			return "OK";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	private void put(String host,String filename,File file) {
		System.out.println("##### update " + host + "/ws/put [" + filename + "]");
		ResteasyClient client = new ResteasyClientBuilder().build();
	    ResteasyWebTarget target = client.target(host + "/ws/put");
	    MultivaluedMap<String,Object> mm=new MultivaluedMapImpl<String,Object>();
	    List<Object> contDis=new ArrayList<Object>();
	    contDis.add("form-data; name=\"uploadedFile\"; filename=\""+filename+"\"");
	    contDis.add(file);
	    mm.put("Content-Disposition",contDis);
	    MultipartFormDataOutput mdo = new MultipartFormDataOutput();
	    mdo.addFormData("uploadedFile", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
	    mdo.getFormData().get("uploadedFile").getHeaders().put("Content-Disposition",contDis);
	    GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(mdo) {};
	    target.request().header("Authorization", "Basic test123").post( Entity.entity(entity, MediaType.MULTIPART_FORM_DATA_TYPE));
	}
	
	private void open(File file) throws Exception{
        try {
    		String command = PropertyUtils.getProperty("app.exec");
    		if(command == null) throw new CommandNotFoundException("No existe el comando para este sistema operativo: " + PropertyUtils.getSystemName());
    		//--------------------------------------------------------
            Process p = Runtime.getRuntime().exec(command.trim() + " " + file);
            
            byte[] bo = new byte[100];
            p.getInputStream().read(bo);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
	}
	
}