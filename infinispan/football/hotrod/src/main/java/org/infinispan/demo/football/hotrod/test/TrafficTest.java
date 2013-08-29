package org.infinispan.demo.football.hotrod.test;

import java.io.IOException;
import java.util.Properties;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

public class TrafficTest {
	
	private static final String JDG_HOST = "jdg.host";
    private static final String HOTROD_PORT = "jdg.hotrod.port";
    private static final String PROPERTIES_FILE = "jdg.properties";
    private static final String CACHE_NAME = "teams";

	public static void main(String[] args) {
		
		Configuration configuration = new ConfigurationBuilder().addServers(jdgProperty(JDG_HOST) + ":" + jdgProperty(HOTROD_PORT)).build();
		RemoteCacheManager cacheManager = new RemoteCacheManager(configuration);
		RemoteCache<String, Object> cache = cacheManager.getCache(CACHE_NAME);
		
		for(int i = 1 ; ; i++) {
			TrafficTestEntity entity = new TrafficTestEntity();
			cache.put(entity.key(), entity.value());
			System.out.println(i);
		}
	}
	
	public static String jdgProperty(String name) {
        Properties props = new Properties();
        try {
            props.load(TrafficTest.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return props.getProperty(name);
    }

}
