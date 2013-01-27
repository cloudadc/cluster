package bootstrap;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.kylin.jbosscache.demo.JBossCacheView;

public class Main {
	
	static final String DEMO_LOG_CONF = "log4j.xml";
	static final String DEMO_LOG = "log";
	static final String DEMO_CONF = "conf";
	static final String DEMO_BIN = "bin";

	static {
		String homePath = System.getProperty("demo.home.dir") ;
		
		if(null == homePath) {
			throw new NullPointerException("Property 'demo.home.dir' is null");
		}
		
		System.setProperty("demo.log.dir", homePath + File.separator + DEMO_LOG);
		System.setProperty("demo.conf.dir", homePath + File.separator + DEMO_CONF);
		System.setProperty("demo.bin.dir", homePath + File.separator + DEMO_BIN);
		
		DOMConfigurator.configure(System.getProperty("demo.conf.dir") + File.separator + DEMO_LOG_CONF);	
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
		logger.debug("    demo.bin.dir : " + System.getProperty("demo.bin.dir"));
		logger.debug("    demo.conf.dir: " + System.getProperty("demo.conf.dir"));
		logger.debug("    demo.log.dir : " + System.getProperty("demo.log.dir"));
	}

	public static void main(String[] args) throws Exception {
		
		logger.info("JBoss Cluster FrameWork Demo Bootstrap");
		
		parseParameters(args);
		
		displayDebugInfo();

		try {
			doStart();
		} catch (Exception e) {
			logger.error("Bootstrap Failed", e);
			throw e;
		}
	}
	
	static void doStart() throws Exception {
		
		validateParams();

		if(mode.compareTo(JGROUPS) == 0) {
			runJGroupsDemo();
		} else if(mode.compareTo(JBOSSCACHE) == 0) {
			runJBossCacheDemo();
		} else if(mode.compareTo(INFINISPAN) == 0) {
			runInfinispanDemo();
		}
	}

	static void validateParams() throws FileNotFoundException {
		
		String confPath = System.getProperty("demo.conf.dir");
		
		configFile = confPath + File.separator + configFile ;
		
		logger.debug("validate " + configFile);
		
		if(!new File(configFile).exists()) {
			
			StringBuffer sb = new StringBuffer();
			sb.append(new File(configFile).getName() + " doesn't exist, available config files: ");
			
			for(File file : new File(confPath).listFiles()) {
				if(file.getName().compareTo(DEMO_LOG_CONF) != 0) {
					sb.append(file.getName() + ", ");
				}
			}
			
			throw new FileNotFoundException(sb.toString());
		}
		
		
	}

	static void runJGroupsDemo() {
		
	}

	static void runJBossCacheDemo() throws Exception {
		new JBossCacheView(useBeanShellConsole, useConsole, configFile, isDebug).doMain();
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
	
	static String configFile = null;
	
	private static void help() {
		
		System.out.println();
		
		System.out.println("[-help] [-h] Lists All Available Commands");
		System.out.println("[-mode/-m <jgroups> <jbosscache> <infinispan>] Selects a JBoss Cluster Framework");
		System.out.println("[-b <bind address>] Binds a address");
		System.out.println("Run JBoss Cluster Framework Demo in Linux:");
		System.out.println("	run.sh [-mode/-m jgroups] [-b <IP>]");
		System.out.println("	run.sh [-mode/-m jbosscache] [-b <IP>] [-console/-bsh] [-debug] [-config <JBossCache Configuration File>]");
		System.out.println("	rim.sh [-mode/-m infinispan] [-b <IP>]");
		System.out.println("Run JBoss Cluster Framework Demo in Windows:");
		System.out.println("	run.bat [-mode/-m jgroups] [-b <IP>]");
		System.out.println("	run.bat [-mode/-m jbosscache] [-b <IP>] [-console/-bsh] [-debug] [-config <JBossCache Configuration File>]");
		System.out.println("	rim.bat [-mode/-m infinispan] [-b <IP>]");

		System.out.println();
		System.out.println("JGroups Params:");
		
		System.out.println();
		System.out.println("JBossCache Params:");
		System.out.println("[-bsh]  Enables The Embedded BeanShell Console");
		System.out.println("[-console]  Enables The Command Line Console");
		System.out.println("[-debug] Enables Print cache content and TreeNode content");
		System.out.println("[-config/-c <configuration file>] Points To A JBossCache Configuration File ");
		

		System.out.println();
		System.out.println("Infinispan Params:");
		
		Runtime.getRuntime().exit(0); 
	}
	
	private static void parseParameters(String[] args) {
		
		for (int i = 0; i < args.length; i++) {
			
			if (args[i].equals("-mode") || args[i].equals("-m")) {
				mode = args[++i];
				continue;
			}
			
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
		
		if(useConsole && useBeanShellConsole) {
			System.out.println("Can not set '-bsh' and '-console' simultaneously");
			help();
		}
		
		if (configFile == null) {
			System.out.println("-config/-c <path to configuration file to use> is mandatory" );
			help();
		}
		
		if(mode == null) {
			System.out.println("-mode/-m <jgroups> <jbosscache> <infinispan> is mandatory" );
			help();
		}
	}

}
