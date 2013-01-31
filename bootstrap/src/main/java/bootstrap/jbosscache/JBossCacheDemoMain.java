package bootstrap.jbosscache;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;

import bootstrap.Bootstrap;

import com.kylin.jbosscache.demo.JBossCacheView;

public class JBossCacheDemoMain extends Bootstrap {
	
	private final static Logger logger = Logger.getLogger(JBossCacheDemoMain.class);

	public static void main(String[] args) throws Exception {
		
		logger.info("JBossCache Demo Bootstrap");
		
		parseParameters(args);
		
		displayDebugInfo(logger);

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
		System.out.println("	run.sh [-b <IP>] [-console/-bsh] [-debug] [-config <JBossCache Configuration File>]");
		System.out.println("Run JBossCache Demo in Windows:");
		System.out.println("	run.bat [-b <IP>] [-console/-bsh] [-debug] [-config <JBossCache Configuration File>]");
		
		
		
		Runtime.getRuntime().exit(0); 
	}
	
	private static void parseParameters(String[] args) {
		
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
		
		if(useConsole && useBeanShellConsole) {
			System.out.println("Can not set '-bsh' and '-console' simultaneously");
			help();
		}
		
		if (configFile == null) {
			System.out.println("-config/-c <path to configuration file to use> is mandatory" );
			help();
		}
	}

}
