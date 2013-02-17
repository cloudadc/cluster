package com.kylin.infinispan.demo;

import com.customized.tools.common.ResourceLoader;

public class Main {

	public static void main(String[] args) throws Exception{
		
		if(args.length < 1) {
			System.out.println("Run Infinispan Demo with Infinispan configuration file, availale configuration file:");
			ResourceLoader.newInstance().printAllFiles("pom.xml");
			Runtime.getRuntime().exit(0);
		}
		
		InfinispanConsole console = new InfinispanConsole(args[0]);
		console.start();
		
	}

}
