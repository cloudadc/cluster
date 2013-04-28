package com.kylin.infinispan.datagrid.helloworld;

import java.io.File;
import java.io.IOException;

import com.customized.tools.common.ResourceLoader;

/**
 * How to build?
 *   mvn clean install dependency:copy-dependencies
 * 
 * How to Run?
 *   java -cp target/dependency/*:target/grid-helloworld.jar -Djava.net.preferIPv4Stack=true com.kylin.infinispan.datagrid.helloworld.Main -c infinispan-distribution.xml -console
 * 
 * @author kylin
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		String configFile = null;
		boolean isConsole = false; 

		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("-b")) {
				System.setProperty("bind.address", args[++i]);
				continue;
			}
			
			if (args[i].equals("-console")) {
				isConsole = true;
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
		
		if(null != System.getProperty("demo.conf.dir")) {
			ResourceLoader.registerBaseDir(System.getProperty("demo.conf.dir"));
		}
		
		if(null == configFile || !new File(configFile).exists()) {
			throw new IllegalArgumentException("Infinispan configFile can not be null, available configFile:" + ResourceLoader.newInstance().getAllConfFiles());
		}
		
		if(isConsole) {
			new DataGridConsole(new CacheDelegateImpl(configFile)).start();
		} else {
			new DataGridTable("infinispan_icon_32px.gif", new CacheDelegateImpl(configFile)).start();
		}
	}

	private static void help() {
		System.out.println("Run Infinispan Data Grid with the following parameters");
		System.out.println("    [-b <IP>] - this will bind a IP adress");
		System.out.println("    [-console] - start Data Grid with console mode");
		System.out.println("    [-config <configFile>] [-c <configFile>] - define Data Grid startup configration file");
		System.out.println("    [-help] [-h] - prompt help info");
		Runtime.getRuntime().exit(0);
	}

}
