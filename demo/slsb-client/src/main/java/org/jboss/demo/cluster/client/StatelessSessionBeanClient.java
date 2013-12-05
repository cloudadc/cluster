package org.jboss.demo.cluster.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;


import org.jboss.demo.cluster.slsb.StatelessSession;

public class StatelessSessionBeanClient {
	
	private String applicationContext  ;
	private String SLSB_JNDI = "ejb:/" + applicationContext + "/StatelessSessionBean!" + StatelessSession.class.getName() ;
	
	private String jndiName = "java:global/cluster-demo-slsb/StatelessSessionBean!org.jboss.demo.cluster.slsb.StatelessSession";
	
	private Context context;
	
	public StatelessSessionBeanClient(String applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	private void execute() throws Exception {
		Hashtable<String, String> jndiProps = new Hashtable<String, String>();
		jndiProps.put( Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming" );
		context = new InitialContext( jndiProps );
		StatelessSession slsb = (StatelessSession)context.lookup(SLSB_JNDI);
		System.out.println(slsb.getServer());
	}



	public static void main(String[] args) throws Exception {
		String applicationContext;
		if (args.length == 0) {
			applicationContext = "cluster-demo-slsb";
		} else {
			applicationContext = args[0];
		}
		new StatelessSessionBeanClient(applicationContext).execute();
	}

}
