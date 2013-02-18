package com.kylin.ejb.remote.client;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class ClientBase {

	protected Context getContext() throws NamingException {
		Context context = new InitialContext();
		return context;
	}

	protected void prompt(Object msg) {
		System.out.println("\n  " + msg);
	}

	protected void stop(Long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public abstract void test() throws Exception;

}