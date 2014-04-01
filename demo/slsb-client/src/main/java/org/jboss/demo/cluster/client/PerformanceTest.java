package org.jboss.demo.cluster.client;

public class PerformanceTest {

	public static void main(String[] args) throws Exception {

		for (;;) {
			new StatelessSessionBeanClient().execute();
			Thread.currentThread().sleep(1000);
		}
	}

}
