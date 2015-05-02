package ar.com.qestudio.agent.backend.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
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
import org.junit.Test;

public class MyResourceTest {

	@Test
	public void get() throws Exception {
		String filename = "impresora.txt";
		String host = "localhost";
		String temp = System.getProperty("java.io.tmpdir") + File.separator + filename;
		// --------------------
		Client client = ClientBuilder.newBuilder().build();
		System.out.println("http://" + host + ":8080/rest/download/" + filename);
		WebTarget target = client.target("http://" + host + ":8080/rest/download/" + filename);
		Response response = target.request().get();
		File remoteFile = response.readEntity(File.class);
		response.close(); // You should close connections!
		// --------------------
		File dest = new File(temp);
		MyResource.copyFileUsingStream(remoteFile, dest);
		MyResource.open(dest);
	}

	@Test
	public void put() throws Exception {
		String filename = "informesemanal3.doc";
		String host = "localhost";
		String temp = System.getProperty("java.io.tmpdir") + File.separator + filename;
		//---------------------
		ResteasyClient client = new ResteasyClientBuilder().build();

	    ResteasyWebTarget target = client.target("http://" + host + ":8080/rest/upload");

	    MultivaluedMap<String,Object> mm=new MultivaluedMapImpl<String,Object>();
	    List<Object> contDis=new ArrayList<Object>();
	    contDis.add("form-data; name=\"uploadedFile\"; filename=\""+filename+"\"");
	    contDis.add(new File(temp));
	    mm.put("Content-Disposition",contDis);
	    
	    MultipartFormDataOutput mdo = new MultipartFormDataOutput();
//	    mdo.addFormData("name", filename, MediaType.TEXT_PLAIN_TYPE);
	    mdo.addFormData("uploadedFile", new File(temp), MediaType.APPLICATION_OCTET_STREAM_TYPE);
	    mdo.getFormData().get("uploadedFile").getHeaders().put("Content-Disposition",contDis);
	    GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(mdo) {};
	    
	    Response r = target.request().post( Entity.entity(entity, MediaType.MULTIPART_FORM_DATA_TYPE));
		// --------------------
		if (r.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + r.getStatus());
		}
	}

	@Test
	public void exec(){
	    /* build up command and launch */
	    String command = "";
	    //String file = "C:\\dev\\impresora.txt";
	    String fullname = "C:\\Users\\Usuario\\AppData\\Local\\Temp\\tutorial.txt";
//	    if (isLinux()) {
//	        command = "xdg-open " + file;
//	    } else if (isWindows()) {
	        command = "cmd /C start /MAX /wait " + fullname;
//	    } else
//	        return;

	    try {
	        Process proc = Runtime.getRuntime().exec(command);
	        System.out.println("waitFor: " + proc.waitFor());
	        System.out.println("······ fin");
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
}