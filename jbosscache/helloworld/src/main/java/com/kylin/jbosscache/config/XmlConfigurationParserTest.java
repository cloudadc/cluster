package com.kylin.jbosscache.config;

import java.io.FileInputStream;
import java.io.InputStream;

import org.jboss.cache.config.Configuration;
import org.jboss.cache.config.parsing.XmlConfigurationParser;

public class XmlConfigurationParserTest {
	
	private String configFile;
	
	public XmlConfigurationParserTest(String configFile) {
		this.configFile = configFile ;
	}
	
	public void test() throws Exception {
		
		InputStream in = new FileInputStream(configFile);
		
		XmlConfigurationParser parser = new XmlConfigurationParser();
		
		Configuration c = parser.parseStream(in);
	}

}
