package com.kylin.infinispan.demo.test;

public class Main {
	
	static {
		System.setProperty("java.net.preferIPv4Stack", "true");
	}

	public static void main(String[] args) throws Exception {

		String[] array = new String[]{"-c", "infinispan-config.xml", "-visible"};
		
		com.kylin.infinispan.demo.Main.main(array);
	}

}
