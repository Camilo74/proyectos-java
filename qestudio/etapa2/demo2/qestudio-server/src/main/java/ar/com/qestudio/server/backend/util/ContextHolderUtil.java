package ar.com.qestudio.server.backend.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * La idea de esta clase es que nos permita acceder a las properties sin tener que declararlo en los mapeos
 * @author damian
 *
 */
public class ContextHolderUtil {

//	private static final Logger logger =  Logger.getLogger(ContextHolderUtil.class);
	private static Properties prop = new Properties();
	
	static {
		try {
			InputStream inputStream = ContextHolderUtil.class.getClassLoader().getResourceAsStream("qestudio-server.properties");
			prop.load(inputStream);
		} catch (Exception e) {
//			logger.error("Error al cargar el archivo de properties",e);
		}
	}
	
	private ContextHolderUtil(){}
	
	public static String getProperty(String key){
		return prop.getProperty(key);
	}
	
}
