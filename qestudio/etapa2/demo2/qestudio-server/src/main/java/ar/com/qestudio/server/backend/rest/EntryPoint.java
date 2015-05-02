package ar.com.qestudio.server.backend.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import ar.com.qestudio.server.FileForm;
import ar.com.qestudio.server.backend.util.ContextHolderUtil;
 
@Path("/rest")
public class EntryPoint {
 
    @GET
    @Path("/status")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() throws UnknownHostException {
        return "ok";
    }
    
	@GET
	@Path("/download/{filename}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response download(@PathParam("filename") String filename) {
		
		String fullpath = ContextHolderUtil.getProperty("qestudio.server.path.local");
		
		File downloadFile = new File(fullpath + File.separator + filename);
		FileInputStream inputStream = null;
		
		try {
			inputStream = new FileInputStream(downloadFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != inputStream)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
 
		}
		
		ResponseBuilder response = Response.ok((Object) downloadFile);
		response.header("Content-Disposition", "attachment; filename=\""+filename+"\"");
		return response.build();

	}
    
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public Response uploadFile(MultipartFormDataInput input) {
 
		String fileName = "";
 
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");
 
		for (InputPart inputPart : inputParts) {
 
		 try {
 
			MultivaluedMap<String, String> header = inputPart.getHeaders();
			fileName = getFileName(header);
 
			//convert the uploaded file to inputstream
			InputStream inputStream = inputPart.getBody(InputStream.class,null);
 
			byte [] bytes = IOUtils.toByteArray(inputStream);
 
			//constructs upload file path
			fileName = ContextHolderUtil.getProperty("qestudio.server.path.local") + File.separator + fileName;
 
			writeFile(bytes,fileName);
 
			System.out.println("Done");
 
		  } catch (IOException e) {
			e.printStackTrace();
		  }
 
		}
 
		return Response.status(200)
		    .entity("uploadFile is called, Uploaded file name : " + fileName).build();
 
	}
 
	/**
	 * header sample
	 * {
	 * 	Content-Type=[image/png], 
	 * 	Content-Disposition=[form-data; name="file"; filename="filename.extension"]
	 * }
	 **/
	//get uploaded filename, is there a easy way in RESTEasy?
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
 
	//save to somewhere
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