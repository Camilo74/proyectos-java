package ar.com.qestudio.agent.backend.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

import ar.com.qestudio.agent.FileForm;
import ar.com.qestudio.agent.backend.util.ContextHolderUtil;

@Path("/")
public class MyResource {

	private static String DEFAULT = "localhost:8180";
	
	@GET
	@Path("/status")
	@Produces(value=MediaType.APPLICATION_JSON)
	public String status(){
		String os = getSystemName();
		return "jsonCallback({'os':'"+os+"'})";
	}
	
	@GET
	@Path("/open")
	public Response get(@QueryParam("hosts") String hosts, @QueryParam("filename") String filename) {
		try {
			File temp = new File(System.getProperty("java.io.tmpdir") + File.separator + filename);
			// --------------------
			Client client = ClientBuilder.newBuilder().build();
			WebTarget target = client.target("http://" + DEFAULT + "/rest/download/" + filename);
			System.out.println("http://" + DEFAULT + "/rest/download/" + filename);
			Response response = target.request().get();
			File remoteFile = response.readEntity(File.class);
			response.close(); // You should close connections!
			File dest = new File(temp.toURI().toURL().getPath());
			copyFileUsingStream(remoteFile, dest);
			java.awt.Desktop.getDesktop().open(dest);
			open(dest);
			System.out.println("$$$$$$$$$$$$$$$");
			put(filename, dest);
			return Response.status(Response.Status.OK).build();
		} catch (ProcessingException e2){
			System.out.println("buscoo la conexion! - " + hosts);
			DEFAULT = getAvailableConnection(hosts.split(","));
			return get(hosts, filename);
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private String getAvailableConnection(String[] hosts){
		for (String h : hosts) {
			if(connection(h)){
				return h;
			}
		}
		return null;
	}
	
	private boolean connection(String host){
		try {
			Client client = ClientBuilder.newBuilder().build();
			WebTarget target = client.target("http://" + host + "/rest/status");
			Response response = target.request().get();
			return response.getStatus() == 200;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void put(String filename,File file) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		
	    ResteasyWebTarget target = client.target("http://" + DEFAULT + "/rest/upload");

	    MultivaluedMap<String,Object> mm=new MultivaluedMapImpl<String,Object>();
	    List<Object> contDis=new ArrayList<Object>();
	    contDis.add("form-data; name=\"uploadedFile\"; filename=\""+filename+"\"");
	    contDis.add(file);
	    mm.put("Content-Disposition",contDis);
	    
	    MultipartFormDataOutput mdo = new MultipartFormDataOutput();
//	    mdo.addFormData("name", filename, MediaType.TEXT_PLAIN_TYPE);
	    mdo.addFormData("uploadedFile", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
	    mdo.getFormData().get("uploadedFile").getHeaders().put("Content-Disposition",contDis);
	    GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(mdo) {};

	    Response r = target.request().header("Authorization", "Basic test123").post( Entity.entity(entity, MediaType.MULTIPART_FORM_DATA_TYPE));
	}
	
//	public static void open(File file) throws Exception{
//		String os = System.getProperty("os.name");
//		String command = ContextHolderUtil.getProperty("qestudio.agent.exec."+os);
//		CommandLine cmdLine = new CommandLine(command);
//		cmdLine.addArgument("${file}");
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("file", file);
//		cmdLine.setSubstitutionMap(map);
//
//		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
//
//		ExecuteWatchdog watchdog = new ExecuteWatchdog(60*1000);
//		Executor executor = new DefaultExecutor();
//		executor.setExitValue(1);
//		executor.setWatchdog(watchdog);
//		executor.execute(cmdLine, resultHandler);
//
//		// some time later the result handler callback was invoked so we
//		// can safely request the exit value
//		resultHandler.waitFor();
//		System.out.println("finalizado");
//	}
	
	public static void open(File file) throws Exception{
        try {
    		String os = getSystemName();
    		String command = ContextHolderUtil.getProperty("qestudio.agent.exec."+os).trim();
    		//--------------------------------------------------------
            Process p = Runtime.getRuntime().exec(command + " " + file);
            
            byte[] bo = new byte[100];
            p.getInputStream().read(bo);
            System.out.println(new String(bo));
            
//            System.out.println("iniciando con proceso numero: " + p);
//            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line = null;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
            System.out.println("##### terminado el proceso numero: " + p);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static String getSystemName(){
		return System.getProperty("os.name").replaceAll(" ", "");
	}
	
	public static void copyFileUsingStream(File source, File dest)
			throws IOException {
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
	
	@POST
	@Path("/metadata2")	
	@Produces("text/html")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String metadata2(@FormParam("filename") String filename) {
		System.out.println("######### filename: " + filename);
		return filename;
	}

	@POST
	@Path("/metadata3")
	@Produces("text/html")
	@Consumes("multipart/form-data")
	public String metadata3(@MultipartForm FileForm input) {
		System.out.println("######### filename: " + input.getFileName());
		return input.getFileName();
	}
	
    @POST
    @Path("/metadata4")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response metadata4(@MultipartForm FileForm input) {
    	System.out.println("######### filename: " + input.getFileName());
    	return Response.ok().build();
    }
}