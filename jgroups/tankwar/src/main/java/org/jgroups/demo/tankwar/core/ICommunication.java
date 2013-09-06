package org.jgroups.demo.tankwar.core;

import org.jgroups.demo.tankwar.jgroups.TankWarCommunicationException;

public interface ICommunication {
	
	/**
	 * 
	 * synchronous send session
	 */
	public Session synchSend(Session session) throws TankWarCommunicationException;
	
	/**
	 * asychronous send session
	 */
	public void asychSend(Session session) throws TankWarCommunicationException;

}
