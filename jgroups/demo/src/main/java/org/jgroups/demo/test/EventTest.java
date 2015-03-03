package org.jgroups.demo.test;

import org.jgroups.Event;

public class EventTest extends TestBase {

	
	public void test() throws Exception {

		System.out.println("'org.jgroups.Event' test");
		
		Event event = new Event(100);
		System.out.println(event.getArg());
		System.out.println(event);
		System.out.println(event.getType());
	}
	
	public static void main(String[] args) throws Exception {

		new EventTest().test();
	}

}
