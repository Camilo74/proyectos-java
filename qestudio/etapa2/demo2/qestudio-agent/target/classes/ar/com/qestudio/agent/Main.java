package ar.com.qestudio.agent;

import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;

import ar.com.qestudio.agent.backend.rest.MyResource;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println(System.getProperty("os.name").replaceAll(" ", ""));
		TJWSEmbeddedJaxrsServer tjws = new TJWSEmbeddedJaxrsServer();
		tjws.setPort(8280);
		tjws.start();
		tjws.getDeployment().getRegistry().addPerRequestResource(MyResource.class);
	}
}
