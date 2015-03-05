package org.jboss.cache.demo;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.jboss.cache.demo.JBossCacheView;

import com.customized.tools.common.ResourceLoader;

public class Main  {
	
	private final static Logger logger = Logger.getLogger(MainTest.class);

	public static void main(String[] args) throws Exception {
		
		logger.info("JBossCache Demo Bootstrap");
		
		if(null != System.getProperty("demo.conf.dir")) {
			ResourceLoader.registerBaseDir(System.getProperty("demo.conf.dir"));
		}
		
		parseParameters(args);
		
		try {
			doStart();
		} catch (Exception e) {
			logger.error("Bootstrap Failed", e);
			throw e;
		}
	}
	
	static void doStart() throws Exception {
		
		validateParams();

		runJBossCacheDemo();
	}

	static void validateParams() throws FileNotFoundException {
		
		String confPath = System.getProperty("demo.conf.dir");
		
		configFile = confPath + File.separator + configFile ;
				
		if(useConsole && useBeanShellConsole) {
			useBeanShellConsole = false;
		}
		
		if (configFile == null || !new File(configFile).exists()) {
			System.out.println("-config/-c <path to configuration file to use> is mandatory, available config files:" + ResourceLoader.newInstance().getAllConfFiles("log4j.xml") );
			help();
		}
	
	}

	static void runJBossCacheDemo() throws Exception {
		new JBossCacheView(useBeanShellConsole, useConsole, configFile, isDebug).doMain();
	}

	static void runInfinispanDemo() {
		
	}
	
	static boolean useBeanShellConsole = false;
	static boolean isDebug = false;
	static boolean useConsole = false;
	
	static String configFile = null;
	
	private static void help() {
		
		System.out.println();
		
		System.out.println("[-help] [-h] Lists All Available Commands");
		System.out.println("[-b <bind address>] Binds a address");
		System.out.println("[-bsh]  Enables The Embedded BeanShell Console");
		System.out.println("[-console]  Enables The Command Line Console");
		System.out.println("[-debug] Enables Print cache content and TreeNode content");
		System.out.println("[-config/-c <configuration file>] Points To A JBossCache Configuration File ");
		System.out.println();
		System.out.println("Run JBossCache Demo in Linux:");
		System.out.println("	jbosscache.sh [-b <IP>] [-console] [-bsh] [-debug] [-config/-c <JBossCache Configuration File>]");
		System.out.println("Run JBossCache Demo in Windows:");
		System.out.println("	jbosscache.bat [-b <IP>] [-console] [-bsh] [-debug] [-config/-c <JBossCache Configuration File>]");
		
		
		
		Runtime.getRuntime().exit(0); 
	}
	
	private static void parseParameters(String[] args) {
		
		try {
			for (int i = 0; i < args.length; i++) {
				
				if (args[i].equals("-b")) {
					System.setProperty("bind.address", args[++i]);
					continue;
				}
				
				if (args[i].equals("-bsh")) {
					useBeanShellConsole = true;
					continue;
				}
				
				if (args[i].equals("-console")) {
					useConsole = true;
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
		} catch (Exception e) {
			help();
		}
	}

}
