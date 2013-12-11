package org.jboss.demo.cluster.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;


import org.jboss.demo.cluster.slsb.StatefulSession;

public class StatefulSessionBeanClient {
	
	private String applicationContext = "cluster-demo-sfsb";
	private String SFSB_JNDI = "ejb:/" + applicationContext + "/StatefulSessionBean!" + StatefulSession.class.getName()  + "?stateful";		
	
	private void execute() throws Exception {
		Hashtable<String, String> jndiProps = new Hashtable<String, String>();
		jndiProps.put( Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming" );
		Context context = new InitialContext( jndiProps );
		StatefulSession sfsb = (StatefulSession)context.lookup(SFSB_JNDI);
		
		System.out.println("Sticky routes Test");
		for(int i = 0 ; i < 10 ; i ++){
			System.out.println(sfsb.getServer());
		}
		
		System.out.println("\nFail Over Test");
		for(int i = 0 ; i < 10 ; i ++) {
			System.out.println(sfsb.getName());
			sfsb.setName("sfsb-test-" + i);
			Thread.currentThread().sleep(1000 * 5);
		}
	}

	public static void main(String[] args) throws Exception {
		new StatefulSessionBeanClient().execute();
	}

}
