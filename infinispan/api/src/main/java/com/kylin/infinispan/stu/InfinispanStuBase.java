package com.kylin.infinispan.stu;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ClusteringConfigurationBuilder;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.HashConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.configuration.global.TransportConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class InfinispanStuBase {

private GlobalConfiguration initGlobalConfiguration() {
		
		GlobalConfigurationBuilder builder = GlobalConfigurationBuilder.defaultClusteredBuilder();
		
		TransportConfigurationBuilder transportConfigurationBuilder = builder.transport();
		
		transportConfigurationBuilder = transportConfigurationBuilder.addProperty("configurationFile", "jgroups.xml");
		
		GlobalConfiguration globalConfiguration = transportConfigurationBuilder.build();
	
		return globalConfiguration ;
	}
	
	private Configuration initConfiguration() {
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		
		ClusteringConfigurationBuilder clusteringConfigurationBuilder = builder.clustering();
		
		clusteringConfigurationBuilder = clusteringConfigurationBuilder.cacheMode(CacheMode.DIST_SYNC);
		
		HashConfigurationBuilder hashConfigurationBuilder = clusteringConfigurationBuilder.hash();
		
		hashConfigurationBuilder = hashConfigurationBuilder.numOwners(2);
		
		Configuration configuration = hashConfigurationBuilder.build();
		
		return configuration ;
	}
	
	public EmbeddedCacheManager getEmbeddedCacheManager() {
		return new DefaultCacheManager(initGlobalConfiguration(), initConfiguration());
	}
}
