package com.kylin.infinispan.demo.test;

/**
 * 
 * How to Build?
 *   mvn clean install dependency:copy-dependencies
 * 
 * How to Run?
 *   java -cp target/infinispan-demo-1.0.jar:target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.infininfinispan-config.xml
 *
 */
public class Main {
	
	static {
		System.setProperty("java.net.preferIPv4Stack", "true");
	}

	public static void main(String[] args) throws Exception {

		String[] array = new String[]{"-c", "infinispan-config.xml", "-visible"};
		
		com.kylin.infinispan.demo.Main.main(array);
	}

}
