package com.kylin.jbosscache.demo;

import org.apache.log4j.xml.DOMConfigurator;

public class Main {
	
	static {
		DOMConfigurator.configure("log4j.xml");
	}
	
	private static void help() {
		
		System.out.println();
		System.out.println("JBossCacheView [-help] " +
    		  		   	   "[-console/-bsh] " +
    		  		  	   "[-debug] " +
    		  			   "[-config <JBossCache Configuration File>]");

		System.out.println();
		System.out.println("[-help] List All Available Commands");
		System.out.println("[-bsh]  Enables The Embedded BeanShell Console");
		System.out.println("[-console]  Enables The Command Line Console");
		System.out.println("[-debug] Enables Print cache content and TreeNode content");
		System.out.println("[-config <configuration file>] Points To A JBossCache Configuration File ");
		System.out.println();
		Runtime.getRuntime().exit(0); 
	}
	
	private static void parseParameters(String[] args) {
		
		for (int i = 0; i < args.length; i++) {
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
			if (args[i].equals("-config")) {
				configurationFile = args[++i];
				continue;
			}
			help();
		}
		
		if(useConsole && useBeanShellConsole) {
			System.out.println("Can not set '-bsh' and '-console' simultaneously");
			help();
		}
		
		if (configurationFile == null) {
			System.out.println("-config <path to configuration file to use> is mandatory" );
			help();
		}
	}

	private static boolean useBeanShellConsole = false;
	private static boolean isDebug = false;
	private static boolean useConsole = false;
	private static String configurationFile = null;
	
	public static void main(String[] args) throws Exception {
		
		parseParameters(args);
		
		JBossCacheView view = new JBossCacheView(useBeanShellConsole, useConsole, configurationFile, isDebug);
		view.doMain();
		
	}
	
}
