package bootstrap;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Bootstrap {

	protected static final String DEMO_LOG_CONF = "log4j.xml";
	protected static final String DEMO_LOG = "log";
	protected static final String DEMO_CONF = "conf";
	protected static final String DEMO_BIN = "bin";

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
	
	protected static void displayDebugInfo(Logger logger) {

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
}
