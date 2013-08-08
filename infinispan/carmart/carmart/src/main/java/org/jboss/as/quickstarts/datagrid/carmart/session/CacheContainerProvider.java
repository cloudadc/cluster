package org.jboss.as.quickstarts.datagrid.carmart.session;

import java.io.IOException;
import java.util.Properties;

import org.infinispan.client.hotrod.RemoteCacheManager;

/**
 * 
 * Subclasses should create an instance of a cache manager (DefaultCacheManager, RemoteCacheManager, etc.)
 *  
 */
public abstract class CacheContainerProvider {

    public static final String DATAGRID_HOST = "datagrid.host";
    public static final String HOTROD_PORT = "datagrid.hotrod.port";
    public static final String PROPERTIES_FILE = "datagrid.properties";

    abstract public RemoteCacheManager getCacheContainer();

    protected String jdgProperty(String name) {
        Properties props = new Properties();
        try {
            props.load(this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return props.getProperty(name);
    }
}
