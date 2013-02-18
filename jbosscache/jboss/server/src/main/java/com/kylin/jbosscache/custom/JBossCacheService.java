package com.kylin.jbosscache.custom;

public interface JBossCacheService {
	
	public void start() throws Exception ;
	
	public void stop() throws Exception ;
	
	public void showCache() throws Exception ;
	
	public void put(int size) throws Exception;
	
	public void put(String fqn, int size) throws Exception;

}
