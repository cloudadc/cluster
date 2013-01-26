package bootstrap;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.kylin.jbosscache.demo.JBossCacheView;

public class Main {
	
	static final String LOG_CONF = "log4j.xml";
	static final String LOG_LOG = "log";

	static {
		String homePath = System.getProperty("demo.home.dir") ;
		
		if(null == homePath) {
			throw new NullPointerException("Property 'demo.home.dir' is null");
		}
		
		System.setProperty("demo.log.dir", homePath + File.separator + LOG_LOG);
		
		DOMConfigurator.configure(homePath + File.separator + LOG_CONF);	
	}

	private final static Logger logger = Logger.getLogger(Main.class);
	
	static void displayDebugInfo() {

		if (!logger.isDebugEnabled())
			return;

		logger.debug("Display Java Information");
		logger.debug("   java.vendor: " + System.getProperty("java.vendor"));
		logger.debug("   java.vm.name: " + System.getProperty("java.vm.name"));
		logger.debug("   java.vm.version: " + System.getProperty("java.vm.version"));
		logger.debug("   java.version: " + System.getProperty("java.version"));
		logger.debug("   java.home: " + System.getProperty("java.home"));
		
		logger.debug("Display OS Information");
		logger.debug("    os.name: " + System.getProperty("os.name"));
		logger.debug("    os.version: " + System.getProperty("os.version"));
		logger.debug("    user.name: " + System.getProperty("user.name"));
		logger.debug("    user.home: " + System.getProperty("user.home"));
		logger.debug("    user.language: " + System.getProperty("user.language"));
		
		logger.debug("Display Demo Information");
		logger.debug("    demo.home.dir: " + System.getProperty("demo.home.dir"));
		logger.debug("    demo.log.dir: " + System.getProperty("demo.log.dir"));
	}

	public static void main(String[] args) throws Exception {
		
		logger.info("JBoss Cluster FrameWork Demo Bootstrap");
		
		parseParameters(args);
		
		displayDebugInfo();

		doStart();
	}
	
	static void doStart() throws Exception {

		if(mode.compareTo(JGROUPS) == 0) {
			runJGroupsDemo();
		} else if(mode.compareTo(JBOSSCACHE) == 0) {
			runJBossCacheDemo();
		} else if(mode.compareTo(INFINISPAN) == 0) {
			runInfinispanDemo();
		}
	}

	static void runJGroupsDemo() {
		
	}

	static void runJBossCacheDemo() throws Exception {
		new JBossCacheView(useBeanShellConsole, useConsole, configurationFile, isDebug).doMain();
	}

	static void runInfinispanDemo() {
		
	}

	static final String JGROUPS = "jgroups";
	static final String JBOSSCACHE = "jbosscache";
	static final String INFINISPAN = "infinispan";
	
	static String mode = null;
	
	static boolean useBeanShellConsole = false;
	static boolean isDebug = false;
	static boolean useConsole = false;
	static String configurationFile = null;
	
	private static void help() {
		
		System.out.println();
		
		System.out.println("[-help] [-h] Lists All Available Commands");
		System.out.println("[-mode <jgroups> <jbosscache> <infinispan>] Selects a JBoss Cluster Framework");
		System.out.println("Run JBoss Cluster Framework Demo in Linux:");
		System.out.println("	run.sh -mode jgroups ");
		System.out.println("	run.sh -mode jbosscache [-console/-bsh] [-debug] [-config <JBossCache Configuration File>]");
		System.out.println("	rim.sh -mode infinispan");
		System.out.println("Run JBoss Cluster Framework Demo in Windows:");
		System.out.println("	run.bat -mode jgroups ");
		System.out.println("	run.bat -mode jbosscache [-console/-bsh] [-debug] [-config <JBossCache Configuration File>]");
		System.out.println("	rim.bat -mode infinispan");

		System.out.println();
		System.out.println("JGroups Params:");
		
		System.out.println();
		System.out.println("JBossCache Params:");
		System.out.println("[-bsh]  Enables The Embedded BeanShell Console");
		System.out.println("[-console]  Enables The Command Line Console");
		System.out.println("[-debug] Enables Print cache content and TreeNode content");
		System.out.println("[-config <configuration file>] Points To A JBossCache Configuration File ");
		

		System.out.println();
		System.out.println("Infinispan Params:");
		
		Runtime.getRuntime().exit(0); 
	}
	
	private static void parseParameters(String[] args) {
		
		for (int i = 0; i < args.length; i++) {
			
			if (args[i].equals("-mode")) {
				mode = args[++i];
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
			
			if (args[i].equals("-config")) {
				configurationFile = args[++i];
				continue;
			}
			
			if (args[i].equals("-help") || args[i].equals("-h")) {
				help();
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
		
		if(mode == null) {
			System.out.println("-mode <jgroups> <jbosscache> <infinispan> is mandatory" );
			help();
		}
	}

}
