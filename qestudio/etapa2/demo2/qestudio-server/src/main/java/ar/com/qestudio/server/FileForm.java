package ar.com.qestudio.server;


import java.io.Serializable;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FileForm implements Serializable{

	private static final long serialVersionUID = -2988617223603603608L;

	private String fileName;
	private byte[] file;
	
	public FileForm() {}
	
	public FileForm(String fileName, byte[] file, String crc) {
		this.fileName = fileName;
		this.file = file;
	}
	

	public byte[] getFile() {
		return file;
	}
	
	@FormParam("fileName")
	@PartType(MediaType.TEXT_PLAIN)
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}

	@FormParam("file")
	@PartType(MediaType.APPLICATION_OCTET_STREAM)
	public void setFile(byte[] file) {
		this.file = file;
	}

}