package com.company.foo.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class ClassFinder {

	public static final String PACK_FULL_PATH = "com.company.foo.";
	public static final String PACK_MODEL_TYPE = "model";
	public static final String PACK_CONTROLLER_TYPE = "web";
	public static final String PACK_BO_TYPE = "bo";
	public static final String PACK_DAO_TYPE = "dao";
	
    private final static char DOT = '.';
    private final static char SLASH = '/';
    private final static String CLASS_SUFFIX = ".class";
    private final static String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the given '%s' package exists?";

    public final static Set<Class<?>> find(final String scannedPackage) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String scannedPath = scannedPackage.replace(DOT, SLASH);
        final Enumeration<URL> resources;
        try {
            resources = classLoader.getResources(scannedPath);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage), e);
        }
        final List<Class<?>> classes = new LinkedList<Class<?>>();
        while (resources.hasMoreElements()) {
            final File file = new File(resources.nextElement().getFile());
            classes.addAll(find(file, scannedPackage));
        }
        
        Set<Class<?>> filterClass = new HashSet<Class<?>>();
        for (Class<?> clazz : classes) {
			if(!clazz.isInterface() && !filterClass.contains(clazz)){
				filterClass.add(clazz);
			}
		}
        
        return filterClass;
    }

    private final static List<Class<?>> find(final File file, final String scannedPackage) {
        final List<Class<?>> classes = new LinkedList<Class<?>>();
        if (file.isDirectory()) {
            for (File nestedFile : file.listFiles()) {
                classes.addAll(find(nestedFile, scannedPackage));
            }
        //File names with the $1, $2 holds the anonymous inner classes, we are not interested on them. 
        } else if (file.getName().endsWith(CLASS_SUFFIX) && !file.getName().contains("$")) {

            final int beginIndex = 0;
            final int endIndex = file.getName().length() - CLASS_SUFFIX.length();
            final String className = file.getName().substring(beginIndex, endIndex);
            try {
                final String resource = scannedPackage + DOT + className;
                classes.add(Class.forName(resource));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }

    public final static boolean exist(String simpleClassName, String type){
    	try {
			return exist(Class.forName(PACK_FULL_PATH + type + "." + simpleClassName));
		} catch (Exception e) {
			return false;
		}
    }
    
    @SuppressWarnings("rawtypes")
	public final static boolean exist(Class clazz){
    	for (Class element : find(PACK_FULL_PATH + PACK_MODEL_TYPE)) {
			if(element.equals(clazz)){
				return true;
			}
		}
    	return false;
    }
    
}