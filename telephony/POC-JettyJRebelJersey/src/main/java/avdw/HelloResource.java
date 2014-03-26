package avdw;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 * streamingoutput
 */
@Path("/hello")
public class HelloResource {

	private static Stack<File> stack = new Stack<File>();
	
	@GET
	@Path("/hellotext")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Hello World PLAIN text";
	}

	@GET
	@Path("/helloxml")
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?><hello> Hello World XML, YAY!!!</hello>";
	}

	@GET
	@Path("/hellohtml")
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<!DOCTYPE html>" +
		"<html>" +
		"<body>" +
		"<audio controls>" +
		  "<source src=\"../../c:/OSDE/entrada.mp3\" type=\"audio/mpeg\">" +
		"Your browser does not support the audio element." +
		"</audio>" +
		"</body>" +
		"</html>";
	}

	@GET
	@Path("/helloimage")
	@Produces("image/png")
	public Response sayHtmlImage() {
		File file = new File("c:\\logo.png");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=image_from_server.png");
		return response.build();
	}

	@GET
	@Path("/helloaudio")
	@Produces("audio/wav")
	public Response sayHtmlAudio() {
		File file = new File("c:\\audio.wav");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=audio.wav");
		return response.build();
	}

	@GET
	@Path("/helloaudio2")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response sayHtmlAudio2() {
		File file = new File("c:\\audio.wav");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=audio2.wav");
		return response.build();
	}

	@GET
	@Path("/get2")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public File sayHtmlAudio3() {
		File file = new File("c:\\OSDE\\junk.aif");
		return file;
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public File sayInputStream() {
		File file = stack.pop();
		System.out.println("#### pop: " + file.getName());
		return file;
	}
	
	//--------------------
	
	@POST
	@Consumes({ "text/xml", "text/plain", MediaType.TEXT_HTML })
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPostHello() {
		return "Hello World Post!";
	}

	// upload

	private static final String FILE_UPLOAD_PATH = "C:\\osde";
	private static final int BUFFER_SIZE = 1024;

	@POST
	@Path("/push")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response fileUpload(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileInfo) throws IOException {
		
		Response.Status respStatus = Response.Status.OK;
		
		if (fileInfo == null) {
			respStatus = Response.Status.INTERNAL_SERVER_ERROR;
		} else {
			final String fileName = fileInfo.getFileName();
			String uploadedFileLocation = FILE_UPLOAD_PATH + File.separator + System.currentTimeMillis() +  fileName;
			try {
				File file = saveToDisc(uploadedInputStream, uploadedFileLocation);
				stack.push(file);
			} catch (Exception e) {
				respStatus = Response.Status.INTERNAL_SERVER_ERROR;
				e.printStackTrace();
			}
		}

		return Response.status(respStatus).build();
	}

	// save uploaded file to the specified location
	private File saveToDisc(final InputStream fileInputStream, final String fileUploadPath) throws IOException {
		File file = File.createTempFile("temp", ".wav");
		final OutputStream out = new FileOutputStream(file);
		int read = 0;
		byte[] bytes = new byte[BUFFER_SIZE];
		while ((read = fileInputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
		return file;
	}
	
//	@POST
//	@Path("/put")
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	public Response fileUploadStack(
//			@FormDataParam("file") InputStream uploadedInputStream,
//			@FormDataParam("file") FormDataContentDisposition fileInfo) throws IOException {
//		
//		Response.Status respStatus = Response.Status.OK;
//		
//		if (fileInfo == null) {
//			respStatus = Response.Status.INTERNAL_SERVER_ERROR;
//		} else {
//			stack.push(uploadedInputStream);
//			
//		}
//
//		return Response.status(respStatus).build();
//	}
	
}