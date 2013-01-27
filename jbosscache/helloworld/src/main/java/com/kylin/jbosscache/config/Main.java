package com.kylin.jbosscache.config;

import org.jboss.cache.Cache;
import org.jboss.cache.CacheFactory;
import org.jboss.cache.DefaultCacheFactory;

public class Main {

	public static void main(String[] args) throws Exception {
		
		System.out.println(Main.class + " Start");

		String configFile = "/home/kylin/work/project/TankWar/bootstrap/resources/conf/total-replication.xml";
		
//		CacheFactory<String, String> factory = new DefaultCacheFactory<String, String>();
//		Cache<String, String> cache = factory.createCache(configFile, false);
//		cache.start();
		
		new XmlConfigurationParserTest(configFile).test();
		
		System.out.println("DONE");
	}

}
