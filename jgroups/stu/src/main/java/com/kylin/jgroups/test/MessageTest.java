package com.kylin.jgroups.test;

import org.apache.log4j.Logger;
import org.jgroups.Message;

import com.kylin.jgroups.TestBase;

public class MessageTest extends TestBase {
	
	private static final Logger logger = Logger.getLogger(MessageTest.class);

	public void test() throws Exception {
		
		logger.info("'org.jgroups.Message' test");
		
		Message msg = new Message(null, "Hello World");
		logger.info(msg.getHeaders());

	}

	public static void main(String[] args) throws Exception {

		new MessageTest().test();
	}

}
