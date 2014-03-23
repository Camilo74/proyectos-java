package ar.com.qestudio.server.backend.socket;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ar.com.qestudio.core.model.Note;
import ar.com.qestudio.core.model.Registry;
import ar.com.qestudio.server.bo.RegistryBO;

public class RegistroTest {

	private final static Logger logger = Logger.getLogger(RegistroTest.class);
	private RegistryBO registryBO;
	
	@Before
	public void before(){
		//iniciamos el contexto de spring
		ApplicationContext context = new ClassPathXmlApplicationContext("application-root-spring.xml");
		//buscamos el bean de spring
		registryBO = (RegistryBO) context.getBean("registry.bo");
	}
	
	@Test
	public void run(){
		Registry registro = registryBO.find(1);
		Assert.assertNotNull("El objeto es nulo", registro);
		for (Note element : registro.getNotes()) {
			logger.info(element.getDescription());
		}
	}
	
}