package com.kylin.jbosscluster.bootstrap;

import org.apache.log4j.xml.DOMConfigurator;

public class Main {
	
	static {
		DOMConfigurator.configure("log4j.xml");
	}
//	
//	private static final Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		
//		logger.info("JBoss Cluster Demo Bootstrap");
		
		for(String str : args) {
			System.out.println(str);
		}
	}

}
