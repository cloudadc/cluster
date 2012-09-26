package com.kylin.tankwar.jgroups;

import java.io.Serializable;

import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;

public abstract class Communication implements ICommunication, Serializable{

	private static final long serialVersionUID = -1513301563004884546L;
	
	protected static final String CLUSTER_NAME = "TankWarCluster";

	protected JChannel channel;
	
	protected Session session;
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public Session getSession() {
		return session;
	}

	public Session synchSend(Session session) throws TankWarCommunicationException {
		return null;
	}

	public void asychSend(Session session, ReceiverAdapter receive) throws TankWarCommunicationException {
		
	}

	public abstract void connect(String props, String name);
	
	public abstract void close(); 

}
