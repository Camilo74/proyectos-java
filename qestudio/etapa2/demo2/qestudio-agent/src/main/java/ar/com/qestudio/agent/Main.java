package ar.com.qestudio.agent;

import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;

import ar.com.qestudio.agent.backend.rest.RegisterWebService;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println(System.getProperty("os.name").replaceAll(" ", ""));
		TJWSEmbeddedJaxrsServer tjws = new TJWSEmbeddedJaxrsServer();
		tjws.setPort(8280);
		tjws.getDeployment().setApplicationClass(RegisterWebService.class.getCanonicalName());
		tjws.start();
	}
}
