package com.kylin.infinispan.clusteredcache.replication;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import com.kylin.infinispan.clusteredcache.util.ClusterValidation;

import java.io.IOException;

import static org.infinispan.config.Configuration.CacheMode.REPL_SYNC;

@SuppressWarnings("unused")
public abstract class AbstractNode {
   
   private static EmbeddedCacheManager createCacheManagerProgramatically() {
	   
      return new DefaultCacheManager(
            GlobalConfigurationBuilder.defaultClusteredBuilder().transport().addProperty("configurationFile", "jgroups.xml").build(),
            new ConfigurationBuilder().clustering().cacheMode(CacheMode.REPL_SYNC).build());
   }

   private static EmbeddedCacheManager createCacheManagerFromXml() throws IOException {
      return new DefaultCacheManager("infinispan-replication.xml");
   }
   
   public static final int CLUSTER_SIZE = 2;

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
