package com.kylin.infinispan.stu.api;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ClusteringConfigurationBuilder;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.HashConfigurationBuilder;


public class GlobalConfigurationBuilderTest {

	public static void main(String[] args) {
		
		ConfigurationBuilder builder = new ConfigurationBuilder();
		
		ClusteringConfigurationBuilder clusteringConfigurationBuilder = builder.clustering();
		
		clusteringConfigurationBuilder = clusteringConfigurationBuilder.cacheMode(CacheMode.DIST_SYNC);
		
		HashConfigurationBuilder hashConfigurationBuilder = clusteringConfigurationBuilder.hash();
		
		hashConfigurationBuilder = hashConfigurationBuilder.numOwners(2);
		
		Configuration configuration = hashConfigurationBuilder.build();
		
		System.out.println("DONE");
	}
}
