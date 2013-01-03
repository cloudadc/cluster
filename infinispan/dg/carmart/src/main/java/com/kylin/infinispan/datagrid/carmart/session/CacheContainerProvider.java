package com.kylin.infinispan.datagrid.carmart.session;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.infinispan.api.BasicCacheContainer;

/**
 * 
 * Subclasses should create an instance of a cache manager (DefaultCacheManager, 
 * RemoteCacheManager, etc.)
 */
public abstract class CacheContainerProvider {

   public static final String DATAGRID_HOST = "datagrid.host"; 
   public static final String HOTROD_PORT = "datagrid.hotrod.port"; 
   public static final String PROPERTIES_FILE = "META-INF" + File.separator + "datagrid.properties";
   
   abstract public BasicCacheContainer getCacheContainer();
   
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
