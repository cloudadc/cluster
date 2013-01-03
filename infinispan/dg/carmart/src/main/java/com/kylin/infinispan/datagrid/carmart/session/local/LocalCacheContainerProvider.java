package com.kylin.infinispan.datagrid.carmart.session.local;

import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import org.infinispan.api.BasicCacheContainer;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.util.concurrent.IsolationLevel;

import com.kylin.infinispan.datagrid.carmart.session.CacheContainerProvider;


/**
 * {@link CacheContainerProvider}'s implementation creating a DefaultCacheManager 
 * which is configured programmatically. Infinispan's libraries need to be bundled 
 * with the application - this is called "library" mode.
 */
@ApplicationScoped
public class LocalCacheContainerProvider extends CacheContainerProvider {
	
    private Logger log = Logger.getLogger(this.getClass().getName());

    private BasicCacheContainer manager;

    public BasicCacheContainer getCacheContainer() {
    	
        if (manager == null) {
            GlobalConfiguration glob = new GlobalConfigurationBuilder()
                .nonClusteredDefault() //Helper method that gets you a default constructed GlobalConfiguration, preconfigured for use in LOCAL mode
                .globalJmxStatistics().enable() //This method allows enables the jmx statistics of the global configuration.
                .build(); //Builds  the GlobalConfiguration object
            Configuration loc = new ConfigurationBuilder()
                .jmxStatistics().enable() //Enable JMX statistics
                .clustering().cacheMode(CacheMode.LOCAL) //Set Cache mode to LOCAL - Data is not replicated.
                .locking().isolationLevel(IsolationLevel.REPEATABLE_READ) //Sets the isolation level of locking
                .eviction().maxEntries(4).strategy(EvictionStrategy.LIRS) //Sets  4 as maximum number of entries in a cache instance and uses the LIRS strategy - an efficient low inter-reference recency set replacement policy to improve buffer cache performance
                .loaders().passivation(false).addFileCacheStore().purgeOnStartup(true) //Disable passivation and adds a FileCacheStore that is Purged on Startup
                .build(); //Builds the Configuration object
            manager = new DefaultCacheManager(glob, loc, true);
            log.info("=== Using DefaultCacheManager (library mode) ===");
        }
        return manager;
    }

    @PreDestroy
    public void cleanUp() {
        manager.stop();
        manager = null;
    }
}
