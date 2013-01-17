package com.kylin.jbosscache.test.log;

import org.apache.log4j.xml.DOMConfigurator;

public class Runner {
	
	static {
		DOMConfigurator.configure("log4j.xml");
	}
	
	public static void main(String[] args) {

		new CommonsLoggingTest().test();
		
		new Log4JLoggingTest().test();
		
		new JavaLoggingTest().test();
		
	}

}
