package com.kylin.infinispan.clusteredcache.distribution;

import java.io.IOException;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class ClusterConfiguration {
	
	private static EmbeddedCacheManager createCacheManagerFromXml() throws IOException {
		return new DefaultCacheManager("infinispan-replication.xml");
	}


	public static void main(String[] args) throws IOException {

		//Add the Default Cluster Configuration
		Configuration Configuration = new ConfigurationBuilder().clustering().cacheMode(CacheMode.REPL_SYNC).build();
		
		// Customize the Default Cluster Configuration
		GlobalConfiguration globalConfiguration = new GlobalConfigurationBuilder().transport().addProperty("configurationFile", "jgroups.xml").build();
	
		//Configure the Replicated Data Grid
		EmbeddedCacheManager cacheManager = new DefaultCacheManager(globalConfiguration, Configuration);
		
		cacheManager = new DefaultCacheManager("infinispan-replication.xml");
		
		Configuration = new ConfigurationBuilder().clustering().cacheMode(CacheMode.DIST_SYNC).hash().numOwners(2).build();
	}

}
