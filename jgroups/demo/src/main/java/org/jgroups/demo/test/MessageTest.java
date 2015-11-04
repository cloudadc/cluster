package org.jgroups.demo.test;

import org.jgroups.Message;

public class MessageTest extends TestBase {
	

	public void test() throws Exception {
		
		System.out.println("'org.jgroups.Message' test");
		
		Message msg = new Message(null, "Hello World");
		System.out.println(msg.getHeaders());

	}

	public static void main(String[] args) throws Exception {

		new MessageTest().test();
	}

}
