package com.kylin.ejb.remote.client;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.kylin.infinispan.custom.InfinispanService;
import com.kylin.infinispan.custom.annotation.Test;

public abstract class ClientBase {
	
	protected static final String STR_JNDI = "ejb:/remote-server/InfinispanSession!" + InfinispanService.class.getName() ;

	protected Context getContext() throws NamingException {
		
		Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
		jndiProperties.put(Context.URL_PKG_PREFIXES,"org.jboss.ejb.client.naming");
		Context context = new InitialContext(jndiProperties);
		
		return context;
	}
	
	@Test(name = "Can not work temporarily")
	protected Context getContext(String ip) throws NamingException {
		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
		props.setProperty("java.naming.provider.url", "remote://" + ip +":4447");
		props.setProperty("jboss.naming.client.ejb.context", "true");
//		props.setProperty("java.naming.factory.url.pkgs", "org.jboss.ejb.client.naming");
		return new InitialContext(props);
	}

	protected void prompt(Object msg) {
		System.out.println("\n  " + msg);
	}

	protected void stop(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public abstract void test() throws Exception;

}