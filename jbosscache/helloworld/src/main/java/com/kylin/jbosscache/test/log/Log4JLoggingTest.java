package com.kylin.jbosscache.test.log;

import org.apache.log4j.Logger;

public class Log4JLoggingTest {
	
	private static final Logger log = Logger.getLogger(Log4JLoggingTest.class);

	public void test() {
		log.debug("debug");
		log.debug("debug exception", new Exception("debug"));
		
		log.error("error");
		log.error("error exception", new Exception("error"));
		
		log.fatal("fatal");
		log.fatal("fatal exception", new Exception("fatal"));
		
		log.info("info");
		log.info("info exception", new Exception("info"));
		
		log.warn("warn");
		log.warn("warn exception", new Exception("warn"));
		
		log.trace("trace");
		log.trace("trace exception", new Exception("trace"));
	}
}
