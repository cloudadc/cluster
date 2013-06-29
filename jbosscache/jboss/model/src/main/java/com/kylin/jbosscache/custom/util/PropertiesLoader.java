package com.kylin.jbosscache.custom.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

	private static Properties props = null;
	
	private static Properties getProperties() {
		if(null == props) {
			props = new Properties();
			InputStream in = null;
			
			try {
				in = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources.properties");
				props.load(in);
			} catch (IOException e) {
				throw new IllegalArgumentException("load resources.properties eeor", e);
			}
		}
		return props;
	}
	
	public static String getServiceName(){
		return getProperties().getProperty("custom.cache.service");
	}
	
	public static String getCacheName(){
		return getProperties().getProperty("custom.cache.name");
	}
}
