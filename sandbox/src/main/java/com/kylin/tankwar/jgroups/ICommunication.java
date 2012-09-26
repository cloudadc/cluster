package com.kylin.tankwar.jgroups;

import org.jgroups.ReceiverAdapter;

public interface ICommunication {
	
	/**
	 * 
	 * synchronous send session
	 */
	public Session synchSend(Session session) throws TankWarCommunicationException;
	
	/**
	 * asychronous send session
	 */
	public void asychSend(Session session, ReceiverAdapter receive) throws TankWarCommunicationException;

}
