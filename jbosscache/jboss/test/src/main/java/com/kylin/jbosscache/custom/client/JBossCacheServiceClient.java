package com.kylin.jbosscache.custom.client;

import javax.naming.NamingException;


import com.kylin.jbosscache.custom.JBossCacheService;


public class JBossCacheServiceClient extends ClientBase {
	
	private JBossCacheService service ;
	
	public JBossCacheServiceClient() throws NamingException {
		service = (JBossCacheService) getContext().lookup(STR_JNDI);
	}

	public void test() throws Exception {	
		service.addCacheContent("/a/b/c", "k1", "v1");
		service.addCacheContent("/a/d", "k1", "v1");
	}
	
	

	public static void main(String[] args) throws Exception {
		new JBossCacheServiceClient().test();
	
	}
	
}
