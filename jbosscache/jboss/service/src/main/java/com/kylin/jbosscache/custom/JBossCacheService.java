package com.kylin.jbosscache.custom;

import java.util.List;
import java.util.Map;

import com.kylin.jbosscache.custom.model.CacheEntity;
import com.kylin.jbosscache.custom.model.NodeEntity;

public interface JBossCacheService {
	
	// for JBossCache Replication Demo GWT UI
	public List<CacheEntity> getCacheContent(String fqn)  throws Exception;
	public NodeEntity initTree()  throws Exception;
	public void addCacheContent(String fqn, String key, String value)  throws Exception;
	
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
