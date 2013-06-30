package com.kylin.jbosscache.custom.client;

import javax.naming.NamingException;


import com.kylin.jbosscache.custom.service.JBossCacheService;


public class JBossCacheServiceClient extends ClientBase {
	
	private JBossCacheService service ;
	
	public JBossCacheServiceClient() throws NamingException {
		service = (JBossCacheService) getContext().lookup(STR_JNDI);
	}

	public void test() throws Exception {	
		System.out.println(service.getFqnStrs());
		System.out.println(service.getCacheNodeContent("/a/b/c"));
	}
	
	

	public static void main(String[] args) throws Exception {
		new JBossCacheServiceClient().test();
	
	}
	
}
