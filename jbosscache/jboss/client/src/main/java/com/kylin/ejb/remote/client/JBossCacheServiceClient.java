package com.kylin.ejb.remote.client;

import javax.naming.NamingException;


import com.kylin.jbosscache.custom.JBossCacheService;


public class JBossCacheServiceClient extends ClientBase {
	
	private JBossCacheService service ;
	
	public JBossCacheServiceClient() throws NamingException {
		service = (JBossCacheService) getContext().lookup(STR_JNDI);
	}

	public void test() throws Exception {	
		
	}
	
	public void testCache() throws Exception {
		service.showCache();
	}
	
	public void testPut() throws Exception {
//		service.put(10000000);
		service.put("/a/b/c/d", 10000000);
	}

	public static void main(String[] args) throws Exception {
		JBossCacheServiceClient client = new JBossCacheServiceClient();
		
//		client.testCache();
		client.testPut();
	}
	
}
