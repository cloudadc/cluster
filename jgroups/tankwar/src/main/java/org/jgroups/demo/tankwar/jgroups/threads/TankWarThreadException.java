package org.jgroups.demo.tankwar.jgroups.threads;

public class TankWarThreadException extends RuntimeException {

	private static final long serialVersionUID = 3706125349491557291L;

	public TankWarThreadException(String msg, Throwable t) {
		super(msg, t);
	}
}
