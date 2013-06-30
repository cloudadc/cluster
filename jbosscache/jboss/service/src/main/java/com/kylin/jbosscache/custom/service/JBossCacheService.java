package com.kylin.jbosscache.custom.service;

import java.util.List;
import java.util.Map;

public interface JBossCacheService {
	
	// for JBossCache Replication Demo GWT UI
	public void addCacheContent(String fqn, String key, String value)  throws Exception;
	public List<String> getFqnStrs() throws Exception;
	public Map getCacheNodeContent(String fqn)  throws Exception;
	
	//for JBossCache Feature Test
	public void lookup() throws Exception ;
	public void destory() throws Exception ;
	public void showCache() throws Exception ;
	public void put(int size) throws Exception;
	public void put(String fqn, int size) throws Exception;
	public void put(String fqn, Map map) throws Exception;
	public Map get(String fqn) throws Exception ;
	
	public void test();

}
