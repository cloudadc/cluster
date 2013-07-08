package com.kylin.jbosscache.ejb.client;

import javax.naming.NamingException;

import com.kylin.jbosscache.ejb.CounterService;

public class CounterServiceClient extends ClientBase {
	
	private CounterService service;
	
	public CounterServiceClient() throws NamingException {
		service = (CounterService) getContext().lookup(STR_JNDI_COUNTER);
	}
	
	public CounterServiceClient(String ip1, String ip2) throws NamingException {
		service = (CounterService) getContext(ip1, ip2).lookup(STR_JNDI_COUNTER);
	}

	public void test() throws Exception {
		for(int i = 0 ; i < 10 ; i ++) {
			System.out.println("counter value: " + service.count());
		}
	}

	public static void main(String[] args) throws Exception {
//		new CounterServiceClient().test();
		new CounterServiceClient("", "").test();
	}

}
