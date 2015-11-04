package org.jboss.cache.demo;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.CacheStatus;
import org.jboss.cache.DefaultCacheFactory;

import com.customized.tools.cli.TreeNode;

/**
 * Graphical view of a JBoss Cache instance.  Think of it as a view in an MVC model.  An instance of this view
 * needs to be given a reference to the  model ({@link JBossCacheDelegate}) and needs to registers as a
 * {@link org.jboss.cache.CacheListener}. Changes to the cache structure are propagated from the model to the
 * view (via the CacheListener interface), changes from the GUI (e.g. by a user) are executed on the cache model
 * which will delegate on the actual Cache instance (which, if clustered, will broadcast the changes to all replicas).
 * <p/>
 * The view itself maintains references to the nodes, but doesn't cache any of the data associated with the nodes. When
 * data needs to be displayed, the underlying cache will be accessed directly.
 *
 */
public class JBossCacheView {
	
	private static final Logger log = Logger.getLogger(JBossCacheView.class);

   /**
    * A reference to the Swing GUI that displays contents to the user.
    */
   private JBossCacheGUI gui = null;
   
   /**
    * Tree architecture Command Line Console
    */
   private JBossCacheConsole console = null;

   /**
    * JBossCache instance delegate
    */
   private JBossCacheDelegate cacheDelegate;
   
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

   /**
    * Specify a configuration file
    */
   private String configurationFile = null;
   
   public JBossCacheView(boolean useBeanShellConsole, boolean useConsole, String configurationFile, boolean isDebug) {
	   this.useBeanShellConsole = useBeanShellConsole ;
	   this.useConsole = useConsole ;
	   this.configurationFile = configurationFile ;
	   this.isDebug = isDebug ;
	   
	   log.info("JBossCacheView Constructed");
	   
	   if(log.isDebugEnabled()) {
		   log.debug("useBeanShellConsole: " + useBeanShellConsole);
		   log.debug("useConsole: " + useConsole);
		   log.debug("isDebug: " + isDebug);
		   log.debug("configurationFile: " + configurationFile);
	   }
   }
   
   public JBossCacheView(boolean useBeanShellConsole, boolean useConsole, String configurationFile, boolean isDebug, JBossCacheDelegate cacheDelegate) {
	   this(useBeanShellConsole, useConsole, configurationFile, isDebug);
	   setCacheDelegate(cacheDelegate);
   }
   
   public JBossCacheView(boolean useBeanShellConsole, boolean useConsole, boolean isDebug, JBossCacheDelegate cacheDelegate) {
	   this(useBeanShellConsole, useConsole, null, isDebug);
	   setCacheDelegate(cacheDelegate);
   }
   
	public String getConfigurationFile() {
      return configurationFile;
   }

	public void setCacheDelegate(JBossCacheDelegate cacheDelegate) {
      this.cacheDelegate = cacheDelegate;
   }

	public void doMain() throws Exception {
		
		log.info("JBossCache Demo doMain()");
      
		if(null == cacheDelegate) {
			cacheDelegate = createCacheDelegate();
		}
		
		if(!cacheDelegate.getGenericCache().getCacheStatus().equals(CacheStatus.STARTED)) {
			cacheDelegate.getGenericCache().start();
		}
		
		if(log.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("JBossCache Version: " + cacheDelegate.getGenericCache().getVersion() + "\n");
			sb.append("JBossCache Status: " + cacheDelegate.getGenericCache().getCacheStatus() + "\n");
			sb.append("JBossCache ClusterName: " + cacheDelegate.getGenericCache().getConfiguration().getClusterName() + "\n");
			sb.append("JBossCache CacheMode: " + cacheDelegate.getGenericCache().getConfiguration().getCacheMode());
			log.debug(sb.toString());
		}
		
		if(useConsole) {
			startConsole();
		} else {
			startGUI();
		}
   }

	public void startConsole() throws IOException {
		
		if(console == null) {
			log.info("start(): creating the Console");
			console = createConsole("JBossCache", null, cacheDelegate, isDebug, isDebug);
		}
		console.start();
	}

	public void startGUI() throws Exception {
		
		if (gui == null) {
			log.info("start(): creating the GUI");
			gui = createGUI(cacheDelegate, useBeanShellConsole, isDebug);
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

	protected JBossCacheDelegate createCacheDelegate() throws Exception {
		
		log.info("Create JBossCache Delegate wrap a Cache instance");
		
		CacheFactory<String, String> factory = new DefaultCacheFactory<String, String>();
		Cache<String, String> cache = factory.createCache(configurationFile, false);
		JBossCacheDelegate delegate = new JBossCacheDelegateImpl();
		delegate.setCacheShellVariable(cache);

		return delegate;
	}
	
	protected JBossCacheConsole createConsole(String name, TreeNode currentNode, JBossCacheDelegate cacheDelegate, boolean isDebugCache, boolean isDebugTreeNode) {
		return new JBossCacheConsole(name, currentNode, cacheDelegate, isDebugCache, isDebugTreeNode);
	}

	protected JBossCacheGUI createGUI(JBossCacheDelegate delegate, boolean useConsole, boolean debugCache) throws Exception {
		return new JBossCacheGUI(delegate, useConsole, debugCache);
	}
	
}

