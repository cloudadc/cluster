package org.jboss.demo.cluster.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;


import org.jboss.demo.cluster.slsb.StatelessSession;

public class StatelessSessionBeanClient {
	
	private String applicationContext = "cluster-demo-slsb";
	private String SLSB_JNDI = "ejb:/" + applicationContext + "/StatelessSessionBean!" + StatelessSession.class.getName() ;		
	
	private void execute() throws Exception {
		Hashtable<String, String> jndiProps = new Hashtable<String, String>();
		jndiProps.put( Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming" );
		Context context = new InitialContext( jndiProps );
		StatelessSession slsb = (StatelessSession)context.lookup(SLSB_JNDI);
		System.out.println(slsb.getServer());
	}



	public static void main(String[] args) throws Exception {

		new StatelessSessionBeanClient().execute();
	}

}
