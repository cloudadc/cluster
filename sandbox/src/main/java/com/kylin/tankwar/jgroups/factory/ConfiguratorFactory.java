package com.kylin.tankwar.jgroups.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;
import org.jgroups.util.Util;

public class ConfiguratorFactory {
	
	private static final Logger logger = Logger.getLogger(ConfiguratorFactory.class);
	
	static void checkForNullConfiguration(Object properties) {
        if(properties == null)
            throw new NullPointerException("the specifed protocol stack configuration was null");
    }
	
	static void checkJAXPAvailability() {
		
	}
	
	@SuppressWarnings("unused")
	public static InputStream getConfigStream(String properties) throws IOException {
		
		InputStream configStream = null;
		
		configStream = new FileInputStream(properties);
		
		if(configStream == null) {
			configStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(properties);
		}
		
		if(configStream == null)  {
			configStream = new URL(properties).openStream();
		}
		
		if(configStream == null && properties.endsWith("xml")) {
			configStream = Util.getResourceAsStream(properties, ConfiguratorFactory.class);
        }
		
        return configStream;
	}
	
	public static InputStream getConfigStream(File file) throws FileNotFoundException {
		checkForNullConfiguration(file);
		return new FileInputStream(file);
	}
	
	public static InputStream getConfigStream(URL url) throws Exception {
		checkJAXPAvailability();
        return url.openStream();
	}

}
