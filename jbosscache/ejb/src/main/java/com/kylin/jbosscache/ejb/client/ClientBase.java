package com.kylin.jbosscache.ejb.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class ClientBase {
	
	protected static final String STR_JNDI_HELLO = "";
	
	protected static final String STR_JNDI_COUNTER = "";
	
	protected Context getContext() throws NamingException {
		return new InitialContext();
	}
	
	protected Context getContext(String ip1, String ip2) throws NamingException {
		Properties props = new Properties();
		props.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
		props.setProperty("java.naming.provider.url", ip1 +":1100," + ip2 +":1100");
		props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
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