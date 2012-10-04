package com.kylin.tankwar.jgroups.handler;

public class TankWarHandlerException extends RuntimeException {

	private static final long serialVersionUID = -3920186954356854910L;
	
	public TankWarHandlerException(String msg){
		super(msg);
	}
	
	public TankWarHandlerException(Throwable t){
		super(t);
	}
	
	public TankWarHandlerException(String msg, Throwable t){
		super(msg, t);
	}

}
