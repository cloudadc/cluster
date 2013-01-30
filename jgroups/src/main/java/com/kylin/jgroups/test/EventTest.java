package com.kylin.jgroups.test;

import org.apache.log4j.Logger;
import org.jgroups.Event;

import com.kylin.jgroups.TestBase;

public class EventTest extends TestBase {

	private static final Logger logger = Logger.getLogger(EventTest.class);
	
	public void test() throws Exception {

		logger.info("'org.jgroups.Event' test");
		
		Event event = new Event(100);
		logger.info(event.getArg());
		logger.info(event);
		logger.info(event.getType());
	}
	
	public static void main(String[] args) throws Exception {

		new EventTest().test();
	}

}
