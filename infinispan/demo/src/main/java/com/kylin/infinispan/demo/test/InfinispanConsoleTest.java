package com.kylin.infinispan.demo.test;

import java.io.IOException;

import com.kylin.infinispan.demo.InfinispanConsole;

public class InfinispanConsoleTest {

	public static void main(String[] args) throws IOException {

		InfinispanConsole console = new InfinispanConsole("infinispan-config.xml");
		console.start();
	}

}
