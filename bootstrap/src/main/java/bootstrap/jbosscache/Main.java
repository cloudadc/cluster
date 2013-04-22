package bootstrap.jbosscache;

import org.apache.log4j.Logger;

import bootstrap.Bootstrap;


public class Main extends Bootstrap {
	
	private final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		
		logger.info("JBossCache Demo Bootstrap");
				
		displayDebugInfo(logger);
		
		com.kylin.jbosscache.demo.Main.main(args);

	}
	

	

}
