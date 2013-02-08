package com.kylin.infinispan.demo.test;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;

import com.customized.tools.common.ResourceLoader;
import com.kylin.infinispan.demo.CacheListener;

public class CacheTest {

	public static void main(String[] args) throws IOException, XMLStreamException, FactoryConfigurationError {
		
//		BufferedInputStream input = new BufferedInputStream(ResourceLoader.getInstance().getResourceAsStream("infinispan-replication.xml"));
//        XMLStreamReader streamReader = XMLInputFactory.newInstance().createXMLStreamReader(input);

		DefaultCacheManager cacheManager = new DefaultCacheManager(ResourceLoader.getInstance().getResourceAsStream("infinispan-replication.xml"));
		Cache<String, String> cache = cacheManager.getCache("CacheTest");
		cache.addListener(new CacheListener());
		cache.start();
	}

}
