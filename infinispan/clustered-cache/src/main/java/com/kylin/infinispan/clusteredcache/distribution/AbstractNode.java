package com.kylin.infinispan.clusteredcache.distribution;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import com.kylin.infinispan.clusteredcache.util.ClusterValidation;

import java.io.IOException;

@SuppressWarnings("unused")
public abstract class AbstractNode {
   
   private static EmbeddedCacheManager createCacheManagerProgramatically() {
	   
		EmbeddedCacheManager cacheManager = new DefaultCacheManager(
			   GlobalConfigurationBuilder.defaultClusteredBuilder().transport().addProperty("configurationFile", "jgroups.xml").build(),
			   new ConfigurationBuilder().clustering().cacheMode(CacheMode.DIST_SYNC).hash().numOwners(2).build());
	   
		return cacheManager;
   }

   private static EmbeddedCacheManager createCacheManagerFromXml() throws IOException {
	   
	   EmbeddedCacheManager cacheManager = new DefaultCacheManager("infinispan-distribution.xml");
	   
	   return cacheManager;
	   
   }
   
   public static final int CLUSTER_SIZE = 3;

   private final EmbeddedCacheManager cacheManager;
   
   public AbstractNode() {
      this.cacheManager = createCacheManagerProgramatically();
   }
   
   protected EmbeddedCacheManager getCacheManager() {
      return cacheManager;
   }
   
   protected void waitForClusterToForm() {
	   
		if (!ClusterValidation.waitForClusterToForm(getCacheManager(), getNodeId(), CLUSTER_SIZE)) {
			throw new IllegalStateException("Error forming cluster, check the log");
		}
		
   }
   
   protected abstract int getNodeId();
   
}
