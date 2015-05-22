package ar.com.qestudio.agent.backend.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

@ApplicationPath("/api")
public class RegisterWebService extends javax.ws.rs.core.Application {
	Set<Object> singletons;

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> clazzes = new HashSet<Class<?>>();
		clazzes.add(MyResource.class);
		return clazzes;
	}

	@Override
	public Set<Object> getSingletons() {
		if (singletons == null) {
			CorsFilter corsFilter = new CorsFilter();
			corsFilter.getAllowedOrigins().add("*");

			singletons = new HashSet<Object>();
			singletons.add(corsFilter);
		}
		return singletons;
	}
}