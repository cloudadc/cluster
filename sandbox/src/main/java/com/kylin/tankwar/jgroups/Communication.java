package com.kylin.tankwar.jgroups;

import org.jgroups.JChannel;

import com.kylin.tankwar.core.MainFrame;

public abstract class Communication implements ICommunication{
	
	protected static final String CLUSTER_NAME = "TankWarCluster";

	protected JChannel channel;
	
	protected MainFrame mainFrame;

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public Communication(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}

	/**
	 * The session keep all group instance, sessions received from any member in group will be merged to this session.
	 */
	Session session = new Session();
	
	public int getMemberSize() {
		
		if(null == channel) {
			return 0 ;
		} else {
			return channel.getView().getMembers().size();
		}
	}
	
	public String getChannelName() {
		if(null == channel) {
			return "";
		} else {
			return channel.getName();
		}
	}
	
	public Session getSession() {
		return session;
	}

	public Session synchSend(Session session) throws TankWarCommunicationException {
		return null;
	}

	public void asychSend(Session session) throws TankWarCommunicationException {
		
	}

	public abstract void connect(String props, String name);
	
	public abstract void close(); 

}
