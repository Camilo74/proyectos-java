package com.sjl;

import ar.com.qestudio.server.backend.util.ContextHolderUtil;


public class Main
{
	public static void main(String... anArgs) throws Exception
    {
        new Main().start();
    }
	
	private WebServer server;
    
    public Main()
    {
    	int port = Integer.valueOf(ContextHolderUtil.getProperty("qestudio.server.ws.rest.port"));
        server = new WebServer(port,"0.0.0.0");
    }
    
    public void start() throws Exception
    {
        server.start();        
        server.join();
    }
}
