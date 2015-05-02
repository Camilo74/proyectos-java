package com.sjl.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.com.qestudio.server.backend.bo.ListFileBO;
import ar.com.qestudio.server.backend.util.ContextHolderUtil;

@Controller
public class Home {

	@Autowired
	private ListFileBO bo;
	
	@RequestMapping("/")
	public ModelAndView home(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list",bo.getAll());
		map.put("ip", getAddress());
		return new ModelAndView("index",map);
	}
	
	@RequestMapping(value="/json", method=RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Set<String> json(){
		return bo.getAll();
	}

	@RequestMapping(value = "/download/{filename}", method = RequestMethod.GET)
	public @ResponseBody void downloadFiles(@PathVariable("filename") String filename, HttpServletRequest request,HttpServletResponse response) {
		System.out.println(filename);
		ServletContext context = request.getServletContext();
		
		String fullpath = ContextHolderUtil.getProperty("qestudio.server.path.local");
		
		File downloadFile = new File(fullpath + File.separator + filename);
		FileInputStream inputStream = null;
		OutputStream outStream = null;
		
		try {
			inputStream = new FileInputStream(downloadFile);
 
			response.setContentLength((int) downloadFile.length());
			response.setContentType(context.getMimeType(fullpath + File.separator + filename));
 
			// response header
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",downloadFile.getName());
			response.setHeader(headerKey, headerValue);
 
			// Write response
			outStream = response.getOutputStream();
			IOUtils.copy(inputStream, outStream);
 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != inputStream)
					inputStream.close();
				if (null != inputStream)
					outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
 
		}
 
	}
	
    private String getLocalAddress(){
    	String port = ContextHolderUtil.getProperty("qestudio.server.ws.rest.port");
    	String hosts = "";
        try {
            Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
            while( b.hasMoreElements()){
                for ( InterfaceAddress f : b.nextElement().getInterfaceAddresses())
                    if ( f.getAddress().isSiteLocalAddress())
                        hosts+=f.getAddress().getHostAddress() + ":" + port + ",";
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hosts;
    }
    
    public String getAddress(){
    	String port = ContextHolderUtil.getProperty("qestudio.server.ws.rest.port");
    	String local = getLocalAddress();
    	String remote = getRemoteAddress();
    	return "localhost" + ":" + port + ","  + local + remote;
    }
	
    private String getRemoteAddress() {
    	try {
    		String port = ContextHolderUtil.getProperty("qestudio.server.ws.rest.port");
	    	URL whatismyip = new URL("http://checkip.amazonaws.com");
	    	BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
	    	return in.readLine() + ":" + port; //you get the IP as a String
    	} catch (Exception e) {
    		return null;
    	}
	}
    
    public String getHTML(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
           url = new URL(urlToRead);
           conn = (HttpURLConnection) url.openConnection();
           conn.setRequestMethod("GET");
           rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
           while ((line = rd.readLine()) != null) {
              result += line;
           }
           rd.close();
        } catch (IOException e) {
           e.printStackTrace();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return result;
     }

	public static void main(String[] args) {
		Home home = new Home();
		System.out.println(home.getAddress());
	}
    
	public ListFileBO getBo() {
		return bo;
	}

	public void setBo(ListFileBO bo) {
		this.bo = bo;
	}

}