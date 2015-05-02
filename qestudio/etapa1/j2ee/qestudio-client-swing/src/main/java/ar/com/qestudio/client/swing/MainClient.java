package ar.com.qestudio.client.swing;


import javax.swing.UIManager;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClient {
	
	public static void main(String args[]) {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		//iniciamos el contexto de spring
		new ClassPathXmlApplicationContext("application-root-spring.xml");
	}

}
