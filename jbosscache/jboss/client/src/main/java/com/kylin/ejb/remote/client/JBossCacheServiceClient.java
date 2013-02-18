package com.kylin.ejb.remote.client;

import com.kylin.jbosscache.custom.JBossCacheService;


public class JBossCacheServiceClient extends ClientBase {

	public void test() throws Exception {
		
		String jndi = "CustomSession/remote-com.kylin.jbosscache.custom.JBossCacheService";
		JBossCacheService service = (JBossCacheService) getContext().lookup(jndi);
		service.start();
	}

	public static void main(String[] args) throws Exception {
		new JBossCacheServiceClient().test();
	}
	
}
