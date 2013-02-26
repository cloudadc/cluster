package com.kylin.infinispan.custom;

import com.kylin.infinispan.custom.annotation.Test;


public interface InfinispanService {
	
	public void init() throws Exception ;
	
	public void stop() throws Exception ;
	
	public void add(Object key, Object value) throws Exception;
	public void modify(Object key, Object value) throws Exception;
	public Object search(Object key) throws Exception;
	public void delete(Object key) throws Exception;
	
	@Test(name = "Method for Test")
	public void showCacheContainer() throws Exception ;

}
