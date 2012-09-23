package com.kylin.jgroups.test;

import org.apache.log4j.Logger;
import org.jgroups.Address;

import com.kylin.jgroups.TestBase;

public class AddressTest extends TestBase {
	
	private static final Logger logger = Logger.getLogger(AddressTest.class);

	public void test() throws Exception {
		
		logger.info("'org.jgroups.Address' test");
		
		addressbytetest();

	}

	private void addressbytetest() {

		logger.info("Address.NULL: " + Address.NULL);
		logger.info("Address.UUID_ADDR: " + Address.UUID_ADDR);
		logger.info("Address.IP_ADDR: " + Address.IP_ADDR);
	}

	public static void main(String[] args) throws Exception {
		
		new AddressTest().test();
	}

}
