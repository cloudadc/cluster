package org.jboss.demo.cluster.slsb;

public interface StatefulSession {
	public String getServer();
	public String getName();
	public void setName(String name);
}
