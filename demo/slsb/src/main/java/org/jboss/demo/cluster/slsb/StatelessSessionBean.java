package org.jboss.demo.cluster.slsb;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.Clustered;

@Stateless
@Remote(StatelessSession.class)
@Clustered
public class StatelessSessionBean implements StatelessSession {

	public String getServer() {
		return System.getProperty("jboss.server.name");
	}

}
