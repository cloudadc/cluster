package com.kylin.jbosscache.test.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonsLoggingTest {
	
	private static final Log log = LogFactory.getLog(CommonsLoggingTest.class);

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
