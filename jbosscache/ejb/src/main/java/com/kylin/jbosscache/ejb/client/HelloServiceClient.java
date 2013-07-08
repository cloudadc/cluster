package com.kylin.jbosscache.ejb.client;

import javax.naming.NamingException;

import com.kylin.jbosscache.ejb.HelloService;

public class HelloServiceClient extends ClientBase {
	
	private HelloService helloService;
	
	public HelloServiceClient() throws NamingException {
		helloService = (HelloService) getContext().lookup(STR_JNDI_HELLO);
	}
	
	public HelloServiceClient(String ip1, String ip2) throws NamingException {
		helloService = (HelloService) getContext(ip1, ip2).lookup(STR_JNDI_HELLO);
	}

	public void test() throws Exception {
		for(int i = 0 ; i < 10 ; i ++) {
			helloService.sayHello();
		}
	}
	
	public static void main(String[] args) throws Exception {
//		new HelloServiceClient().test();
		new HelloServiceClient("", "").test();
	}

}
