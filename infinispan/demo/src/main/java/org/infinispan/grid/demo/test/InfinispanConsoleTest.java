package org.infinispan.grid.demo.test;

import java.io.IOException;

import org.infinispan.grid.demo.InfinispanConsole;


public class InfinispanConsoleTest {

	public static void main(String[] args) throws IOException {

		InfinispanConsole console = new InfinispanConsole("infinispan-config.xml", false);
		console.start();
	}

}
