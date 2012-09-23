package com.kylin.tankwar.jgroups.demo;

import org.apache.log4j.Logger;

public class Test {
	
	private static final Logger logger = Logger.getLogger(Test.class);

	public static void main(String[] args) {

		A a = new A();
		B b = new B();
		
		logger.info(a == b);
		logger.info(b == a);
		
	}

}
