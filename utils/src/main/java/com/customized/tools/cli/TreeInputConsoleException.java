package com.customized.tools.cli;

public class TreeInputConsoleException extends RuntimeException {

	private static final long serialVersionUID = 445624806959687647L;

	public TreeInputConsoleException(String msg) {
		super(msg);
	}
	
	public TreeInputConsoleException(String msg, Throwable t) {
		super(msg, t);
	}
}
