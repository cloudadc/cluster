package org.infinispan.demo.carmart.session;

import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.demo.carmart.session.CacheContainerProvider;

/**
 * 
 * {@link CacheContainerProvider}'s implementation creating a HotRod client. 
 * JBoss Data Grid server needs to be running and configured properly 
 * so that HotRod client can remotely connect to it - this is called client-server mode.
 * 
 * 
 */
@ApplicationScoped
public class RemoteCacheContainerProvider extends CacheContainerProvider {

    private Logger log = Logger.getLogger(this.getClass().getName());

    private RemoteCacheManager manager;

    public RemoteCacheManager getCacheContainer() {
        if (manager == null) {
        	Configuration configuration = new ConfigurationBuilder().addServers(jdgProperty(DATAGRID_HOST) + ":" + jdgProperty(HOTROD_PORT)).build();
            manager = new RemoteCacheManager(configuration, true);
            log.info("=== Using RemoteCacheManager (Hot Rod) ===");
        }
        return manager;
    }

    @PreDestroy
    public void cleanUp() {
        manager.stop();
        manager = null;
    }
}
