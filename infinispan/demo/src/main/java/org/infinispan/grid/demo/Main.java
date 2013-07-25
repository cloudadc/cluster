package org.infinispan.grid.demo;

import com.customized.tools.common.ResourceLoader;

public class Main {

	public static void main(String[] args) throws Exception{
		
		String configFile = null;
		boolean isVisible = false;
		boolean isDebug = false;
		
		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("-b")) {
				System.setProperty("bind.address", args[++i]);
				continue;
			}

			if (args[i].equals("-visible")) {
				isVisible = true;
				continue;
			}

			if (args[i].equals("-debug")) {
				isDebug = true;
				continue;
			}

			if (args[i].equals("-config") || args[i].equals("-c")) {
				configFile = args[++i];
				continue;
			}

			if (args[i].equals("-help") || args[i].equals("-h")) {
				help();
			}

			help();
		}
		
		InfinispanConsole console = new InfinispanConsole(configFile, isVisible);
		console.start();
		
	}

	private static void help() {

		System.out.println("Run Infinispan Demo with Infinispan configuration file, availale configuration file:");
		ResourceLoader.newInstance().printAllFiles("pom.xml");
		Runtime.getRuntime().exit(0);
	}

}
