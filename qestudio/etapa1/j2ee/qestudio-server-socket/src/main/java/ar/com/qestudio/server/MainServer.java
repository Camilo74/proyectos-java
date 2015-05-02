package ar.com.qestudio.server;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ar.com.qestudio.server.socket.ServerManager;

public class MainServer {
	public static void main(String[] args) {
		//iniciamos el contexto de spring
		ApplicationContext context = new ClassPathXmlApplicationContext("application-root-spring.xml");
		//buscamos el bean de spring
		ServerManager servidor = (ServerManager) context.getBean("server.manager");
		try {
			servidor.execute(4000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}