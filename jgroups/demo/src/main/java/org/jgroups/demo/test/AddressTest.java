package org.jgroups.demo.test;

import org.jgroups.Address;

public class AddressTest extends TestBase {
	
	public void test() throws Exception {
		System.out.println("'org.jgroups.Address' test");
		addressbytetest();
	}

	private void addressbytetest() {

		System.out.println("Address.NULL: " + Address.NULL);
		System.out.println("Address.UUID_ADDR: " + Address.UUID_ADDR);
		System.out.println("Address.IP_ADDR: " + Address.IP_ADDR);
	}

	public static void main(String[] args) throws Exception {
		
		new AddressTest().test();
	}

}
