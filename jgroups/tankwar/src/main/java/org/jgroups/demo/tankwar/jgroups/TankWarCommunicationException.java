package org.jgroups.demo.tankwar.jgroups;

public class TankWarCommunicationException extends RuntimeException {

	private static final long serialVersionUID = -2083358445996966601L;
	
	public TankWarCommunicationException(String msg){
		super(msg);
	}
	
	public TankWarCommunicationException(Throwable t){
		super(t);
	}
	
	public TankWarCommunicationException(String msg, Throwable t){
		super(msg, t);
	}

}
