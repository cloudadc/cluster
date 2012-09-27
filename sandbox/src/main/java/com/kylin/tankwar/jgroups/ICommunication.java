package com.kylin.tankwar.jgroups;

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
