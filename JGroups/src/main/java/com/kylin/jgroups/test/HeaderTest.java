package com.kylin.jgroups.test;

import org.apache.log4j.Logger;
import org.jgroups.Header;
import org.jgroups.protocols.FcHeader;

import com.kylin.jgroups.TestBase;

public class HeaderTest extends TestBase {
	
	private static final Logger logger = Logger.getLogger(HeaderTest.class);

	public void test() throws Exception {

		logger.info("'org.jgroups.Header' test");
		
		byte b = 2;
		Header header = new FcHeader(b);
		logger.info(header);	
	}


	public static void main(String[] args) throws Exception{

		new HeaderTest().test();
	}

}
