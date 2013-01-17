package com.kylin.jbosscache.test.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaLoggingTest {

	private final static Logger log = Logger.getLogger(JavaLoggingTest.class.getName());

	public void test() {
		
		log.log(Level.ALL, "all");
		
		log.log(Level.CONFIG, "config");
		
		log.log(Level.FINE, "fine");
		
		log.log(Level.FINER, "finer");
		
		log.log(Level.INFO, "info");
		
		log.log(Level.OFF, "off");
	
		log.log(Level.SEVERE, "severe");
		
		log.log(Level.WARNING, "warning");
		
	}
}






