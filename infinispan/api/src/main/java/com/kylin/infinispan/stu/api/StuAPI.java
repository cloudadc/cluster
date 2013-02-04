package com.kylin.infinispan.stu.api;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ClusteringConfigurationBuilder;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;

public class StuAPI {

	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();

		StuAPI stu = new StuAPI();
		
		stu.stuConfigurationBuilder();
		
		System.out.println("Stu End, spent " + (System.currentTimeMillis() - start) + " miliseconds");
	}

	private void stuConfigurationBuilder() {
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		ClusteringConfigurationBuilder clusterBuilder = builder.clustering().cacheMode(CacheMode.REPL_SYNC);
		Configuration config = clusterBuilder.build();
	}

}
