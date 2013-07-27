package bootstrap.infinispan;

import java.io.File;

import org.apache.log4j.Logger;

import bootstrap.Bootstrap;

public class Main extends Bootstrap{
	
	private final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		
		logger.info("Infnispan Data Grid Demo Bootstrap");
		
		displayDebugInfo(logger);
		
		for (int i = 0; i < args.length; i++){
			if (args[i].equals("-config") || args[i].equals("-c")){
				args[i+1] = System.getProperty("demo.conf.dir") + File.separator + args[i+1];
			}
		}

		org.infinispan.grid.demo.Main.main(args);
	}

}
