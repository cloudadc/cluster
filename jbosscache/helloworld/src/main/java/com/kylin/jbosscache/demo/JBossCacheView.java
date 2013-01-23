package com.kylin.jbosscache.demo;


import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;



/**
 * Graphical view of a JBoss Cache instance.  Think of it as a view in an MVC model.  An instance of this view
 * needs to be given a reference to the  model ({@link JBossCacheModelDelegate}) and needs to registers as a
 * {@link org.jboss.cache.CacheListener}. Changes to the cache structure are propagated from the model to the
 * view (via the CacheListener interface), changes from the GUI (e.g. by a user) are executed on the cache model
 * which will delegate on the actual Cache instance (which, if clustered, will broadcast the changes to all replicas).
 * <p/>
 * The view itself maintains references to the nodes, but doesn't cache any of the data associated with the nodes. When
 * data needs to be displayed, the underlying cache will be accessed directly.
 *
 */
public class JBossCacheView {
	
	static {
		DOMConfigurator.configure("log4j.xml");
	}
	
   private static Log log = LogFactory.getLog(JBossCacheView.class.getName());

   /**
    * A reference to the Swing GUI that displays contents to the user.
    */
   private JBossCacheGUI gui = null;
   
   /**
    * Tree architecture Command Line Console
    */
   private JBossCacheConsole console = null;

   /**
    * Whether or not to use the embedded BeanShell console.
    */
   private boolean useBeanShellConsole = false;
   
   /**
    * Whether or not to debug TreeNode Node content and cache content
    */
   private boolean isDebug = false;
   
   /**
    * Whether or not to use Command Line Console
    */
   private boolean useConsole = false;

   private String configurationFile = null;

   private JBossCacheModelDelegate cacheModelDelegate;

	public String getConfigurationFile() {
      return configurationFile;
   }

	public void setCacheModelDelegate(JBossCacheModelDelegate cacheModelDelegate) {
      this.cacheModelDelegate = cacheModelDelegate;
      if (gui != null) gui.setCacheModelDelegate(cacheModelDelegate);
   }

	public void doMain(String[] args) throws Exception {
      
		parseParameters(args);

		JBossCacheModelDelegate cacheModelDelegate = createCacheDelegate();
		cacheModelDelegate.getGenericCache().start();
		setCacheModelDelegate(cacheModelDelegate);
		
		if(useConsole) {
			startConsole();
		} else {
			startGUI();
		}
   }

	public void startConsole() throws IOException {
		
		if(console == null) {
			log.info("start(): creating the Console");
			console = new JBossCacheConsole("JBossCache", null, cacheModelDelegate, isDebug, isDebug);
			console.start();
		}
	}

	public void startGUI() throws Exception {
		if (gui == null) {
			log.info("start(): creating the GUI");
			gui = createGUI(cacheModelDelegate, useBeanShellConsole, isDebug);
		}
	}

	public void stop() {
		
		if (gui != null) {
			log.info("stop(): disposing the GUI");
			gui.stopGui();
			gui = null;
		}
		
		if(console != null) {
			log.info("stop(): disposing the Console");
			console.stop();
			console = null;
		}
	}

   /**
    * Starts the view
    *
    * @param args valid arguments are: -console to enable using the embedded beanShell console, -config [path/to/config/file] to specify a config file.
    */
	public static void main(String args[]) {
		try {
			JBossCacheView view = new JBossCacheView();
			view.doMain(args);
		} catch (Exception ex) {
			log.error("Cannot start up!!", ex);
		}
	}

	protected void parseParameters(String[] args) {
		
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
			System.out.println("Can not set '-beanShell' and '-console' simultaneously");
			help();
		}
		
		if (configurationFile == null) {
			System.out.println("-config <path to configuration file to use> is mandatory" );
			help();
		}
	}

	protected JBossCacheModelDelegate createCacheDelegate() throws Exception {
		CacheFactory<String, String> factory = new DefaultCacheFactory<String, String>();
		Cache<String, String> cache = factory.createCache(configurationFile, false);
		JBossCacheModelDelegate delegate = new JBossCacheModelDelegateImpl();
		delegate.setCacheShellVariable(cache);

		return delegate;
	}

	protected JBossCacheGUI createGUI(JBossCacheModelDelegate delegate, boolean useConsole, boolean debugCache) throws Exception {
		return new JBossCacheGUI(delegate, useConsole, debugCache);
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
}












