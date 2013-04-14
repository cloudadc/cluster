package com.kylin.infinispan.datagrid.helloworld;

import org.apache.log4j.Logger;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;

public class MyCacheManagerProvider {
	
	private static final Logger log = Logger.getLogger(MyCacheManagerProvider.class);
	
	private static MyCacheManagerProvider instance = null;
	
	public static MyCacheManagerProvider getInstance() {
		if(null == instance) {
			instance = new MyCacheManagerProvider();
		}
		return instance;
	}
	
	private MyCacheManagerProvider() {
		
	}
	
	private static final long ENTRY_LIFESPAN = 60 * 1000; //60 seconds
	
	private DefaultCacheManager manager;
	
	public void cleanUp() {
		manager.stop();
		manager = null;
	}
	
	 public DefaultCacheManager getCacheManager() {
	      if (manager == null) {
	         log.info("DefaultCacheManager does not exist - constructing a new one");

	         GlobalConfiguration glob = new GlobalConfigurationBuilder()
	               .clusteredDefault() // Builds a default clustered configuration
	               .transport().addProperty("configurationFile", "jgroups-udp.xml") //provide a specific JGroups configuration
	               .globalJmxStatistics().allowDuplicateDomains(true).enable()  //This method enables the jmx statistics of
	                     // the global configuration and allows for duplicate JMX domains
	               .build();  // Builds the GlobalConfiguration object
	         Configuration loc = new ConfigurationBuilder()
	               .jmxStatistics().enable()  //Enable JMX statistics
	               .clustering().cacheMode(CacheMode.DIST_SYNC)  //Set Cache mode to DISTRIBUTED with SYNCHRONOUS replication
	               .hash().numOwners(2) //Keeps two copies of each key/value pair
	               .expiration().lifespan(ENTRY_LIFESPAN) //Set expiration - cache entries expire after some time (given by
	                     // the lifespan parameter) and are removed from the cache (cluster-wide).
	               .build();
	         manager = new DefaultCacheManager(glob, loc, true);
	      }
	      return manager;
	   }

}
