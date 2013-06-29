package com.kylin.jbosscache.custom.client;

import com.kylin.jbosscache.custom.JBossCacheService;


public class JBossCacheServiceClientTest extends ClientBase {

	public void test() throws Exception {

		JBossCacheService service = (JBossCacheService) getContext("127.0.0.1").lookup(STR_JNDI);
		service.test();
	}
	
	public static void main(String[] args) throws Exception {
		new JBossCacheServiceClientTest().test();
	}

}
