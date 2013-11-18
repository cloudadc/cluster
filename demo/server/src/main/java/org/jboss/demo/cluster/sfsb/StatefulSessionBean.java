package org.jboss.demo.cluster.sfsb;

import javax.ejb.Remote;
import javax.ejb.Stateful;

@Stateful
@Remote(StatefulSession.class)
//@Clustered
public class StatefulSessionBean implements StatefulSession {
	
	private String name;

	public String getServer() {
		return System.getProperty( "jboss.server.name" );
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
