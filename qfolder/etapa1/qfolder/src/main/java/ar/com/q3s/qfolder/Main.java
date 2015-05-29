package ar.com.q3s.qfolder;

import ar.com.q3s.qfolder.util.PropertyUtils;
import ar.com.q3s.qfolder.web.JettyServer;

public class Main{
	
	private JettyServer server;
    
    public Main(){
    	Integer port = Integer.valueOf(PropertyUtils.getProperty("qfolder.server.ws.rest.port"));
    	String bindInterface = PropertyUtils.getProperty("qfolder.server.ws.rest.bind.address");
    	//--------------------------------------------
        server = new JettyServer(port,bindInterface);
    }
    
    public void start() throws Exception{
        server.start();        
        server.join();
    }
	
	public static void main(String... anArgs) throws Exception{
        new Main().start();
    }
}