package com.kylin.tankwar.core;

import com.kylin.tankwar.jgroups.TankWarCommunicationException;

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
