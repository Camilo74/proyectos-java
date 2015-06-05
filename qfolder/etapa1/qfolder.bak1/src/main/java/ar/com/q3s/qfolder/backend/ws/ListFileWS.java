package ar.com.q3s.qfolder.backend.ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ar.com.q3s.qfolder.backend.bo.ListFileBO;
import ar.com.q3s.qfolder.util.PropertyUtils;
 
@Path("/ws")
public class ListFileWS {
 
	private static ListFileBO bo;
	
	@GET
    @Path("/status")
    @Produces(MediaType.TEXT_PLAIN)
	@SuppressWarnings("unchecked")
    public Response status() {
    	JSONObject obj = new JSONObject();
    	try {
    		obj.put("name", InetAddress.getLocalHost().getHostName());
    		obj.put("os", PropertyUtils.getSystemName());
    		obj.put("size", bo.getAll().size());
		} catch (Exception e) {
			Response.serverError().entity(e.getMessage()).build();
		}
    	return Response.ok(obj.toJSONString()).build();
    }
    
	@GET
    @Path("/all")
    @Produces(MediaType.TEXT_PLAIN)
	@SuppressWarnings("unchecked")
    public Response all() {
		JSONArray arr = new JSONArray();
    	JSONObject obj = new JSONObject();
    	try {
    		arr.addAll(bo.getAll());
    		obj.put("list", arr);
		} catch (Exception e) {
			Response.serverError().entity(e.getMessage()).build();
		}
    	return Response.ok(obj.toJSONString()).build();
    }	
	
	@GET
	@Path("/get/{filename}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response get(@PathParam("filename") String filename) {
		String fullpath = PropertyUtils.getProperty("qfolder.server.path.local");
		File downloadFile = new File(fullpath + File.separator + filename);
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(downloadFile);
		} catch (Exception e) {
			Response.serverError().entity(e.getMessage()).build();
		} finally {
			try {
				if (null != inputStream)
					inputStream.close();
			} catch (IOException e) {
				Response.serverError().entity(e.getMessage()).build();
			}
 
		}
		ResponseBuilder response = Response.ok((Object) downloadFile);
		response.header("Content-Disposition", "attachment; filename=\""+filename+"\"");
		return response.build();
	}
    
	@POST
	@Path("/put")
	@Consumes("multipart/form-data")
	public Response put(MultipartFormDataInput input) {
		String fileName = "";
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");
		for (InputPart inputPart : inputParts) {
		 try {
			MultivaluedMap<String, String> header = inputPart.getHeaders();
			fileName = getFileName(header);
			System.out.println("#################### " + fileName);
			//convert the uploaded file to inputstream
			InputStream inputStream = inputPart.getBody(InputStream.class,null);
			byte [] bytes = IOUtils.toByteArray(inputStream);
			//constructs upload file path
			fileName = PropertyUtils.getProperty("qfolder.server.path.local") + File.separator + fileName;
			writeFile(bytes,fileName);
			return Response.ok().build();
		  } catch (IOException e) {
			  Response.serverError().entity(e.getMessage()).build();
		  }
		}
		return Response.ok().entity("uploadFile is called, Uploaded file name : " + fileName).build();
	}
	
	//-----------------------------------------------------------------------------------
	
	private String getFileName(MultivaluedMap<String, String> header) {
 
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
 
		for (String filename : contentDisposition) {
			if (filename.trim().startsWith("filename")) {
 
				String[] name = filename.split("=");
 
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
 
	private void writeFile(byte[] content, String filename) throws IOException {
 
		File file = new File(filename);
 
		if (!file.exists()) {
			file.createNewFile();
		}
 
		FileOutputStream fop = new FileOutputStream(file);
 
		fop.write(content);
		fop.flush();
		fop.close();
 
	}

	public ListFileBO getBo() {
		return bo;
	}

	public void setBo(ListFileBO bo) {
		ListFileWS.bo = bo;
	}
	
}