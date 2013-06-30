package com.kylin.jbosscache.custom.gwt.client;

public class JBossCacheGUIException extends RuntimeException {

	private static final long serialVersionUID = 8541157265956079003L;
	
	public JBossCacheGUIException(String msg) {
		super(msg);
	}

	public JBossCacheGUIException(String msg, Throwable t) {
		super(msg, t);
	}
}
