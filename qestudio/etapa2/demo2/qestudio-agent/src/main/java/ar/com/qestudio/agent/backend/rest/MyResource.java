package ar.com.qestudio.agent.backend.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.QueryParam;
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

import ar.com.qestudio.agent.backend.exception.CommandNotFoundException;
import ar.com.qestudio.agent.backend.util.ContextHolderUtil;

@Path("/")
public class MyResource {

	private static String DEFAULT = "localhost:8180";
	
	@GET
	@Path("/status")
	public Response status(){
		String os = getSystemName();
		return Response.ok().entity(os).build();
	}
	
	@GET
	@Path("/open")
	public Response open(@QueryParam("hosts") String hosts, @QueryParam("filename") String filename) {
		try {
			String url = "http://" + DEFAULT + "/rest/download/" + filename;
			System.out.println(url);
			File temp = new File(System.getProperty("java.io.tmpdir") + File.separator + filename);
			// --------------------
			Client client =  new ResteasyClientBuilder()
		    .establishConnectionTimeout(1, TimeUnit.SECONDS)
		    .socketTimeout(1, TimeUnit.SECONDS)
		    .build();
			
			WebTarget target = client.target(url);
			Response response = target.request().get();
			
			if(response.getStatus() != Response.Status.OK.getStatusCode()){
				String clazz = response.getHeaderString("class");
				String message = response.getHeaderString("message");
				//---------------------------------------------------
				throw newException(clazz,message);
			}
			
			
			File remoteFile = response.readEntity(File.class);
			response.close(); // You should close connections!
			File dest = new File(temp.toURI().toURL().getPath());
			copyFileUsingStream(remoteFile, dest);
			open(dest);
			put(filename, dest);
			return Response.ok().build();
		} catch (ProcessingException e2){
			DEFAULT = getAvailableConnection(hosts.split(","));
			if(DEFAULT == null){
				return Response.serverError().entity("No se puede conectar con el servidor de archivos").build();
			}
			return open(hosts, filename);
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Exception newException(String classname, String message) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Class clazz = Class.forName(classname);
		Constructor<?> cons = clazz.getConstructor(String.class);
		return (Exception) cons.newInstance(message);
	}

	private String getAvailableConnection(String[] hosts){
		for (String h : hosts) {
			System.out.println("Intentando conectar a: " + h);
			if(connection(h)){
				System.out.println("Nueva conexion encontrada: " + h);
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
    		String command = ContextHolderUtil.getProperty("qestudio.agent.exec");
    		if(command == null) throw new CommandNotFoundException("No existe el comando para este sistema operativo: " + getSystemName());
    		//--------------------------------------------------------
            Process p = Runtime.getRuntime().exec(command.trim() + " " + file);
            
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
}