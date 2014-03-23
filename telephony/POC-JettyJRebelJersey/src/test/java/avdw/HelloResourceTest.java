package avdw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

/**
 * sox entrada.wav -r 8000 -c1 output.gsm
 */

public class HelloResourceTest {

	public static void main(String[] args) throws FileNotFoundException {
		HelloResourceTest test = new HelloResourceTest();
//		test.send();
		test.play();
	}
	
	public void play(){
		//bajar el inputstram y generar el archivo para escuchar
		try {
			URL oracle = new URL("http://localhost:8081/jersey/hello/get");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(oracle.openStream()));
			
			String inputLine;
			while ((inputLine = in.readLine()) != null){
				System.out.println(inputLine);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void send(){
    	final ClientConfig config = new DefaultClientConfig();
    	final Client client = Client.create(config);
    	
    	final WebResource resource = client.resource("http://localhost:8081/jersey").path("hello").path("put");

    	for (int i = 0; i < 2; i++) {
    		
    		final File fileToUpload = new File("c:\\audio.wav");
    		
    		final FormDataMultiPart multiPart = new FormDataMultiPart();
    		if (fileToUpload != null) {
    			multiPart.bodyPart(new FileDataBodyPart("file", fileToUpload,MediaType.APPLICATION_OCTET_STREAM_TYPE));
    		}
    		
    		final ClientResponse clientResp = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class,multiPart);
    		System.out.println("Response: " + clientResp.getClientResponseStatus());
    		
		}
    	client.destroy();
	}
	
}