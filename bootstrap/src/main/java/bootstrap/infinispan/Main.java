package bootstrap.infinispan;

import org.apache.log4j.Logger;

import bootstrap.Bootstrap;

public class Main extends Bootstrap{
	
	private final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		
		logger.info("Infnispan Data Grid Demo Bootstrap");
		
		displayDebugInfo(logger);

		try {
			org.infinispan.grid.demo.Main.main(args);
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		}
	}

}
