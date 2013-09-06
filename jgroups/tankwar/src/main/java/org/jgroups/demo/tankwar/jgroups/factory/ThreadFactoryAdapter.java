package org.jgroups.demo.tankwar.jgroups.factory;

import java.util.concurrent.ThreadFactory;

public class ThreadFactoryAdapter implements org.jgroups.util.ThreadFactory{
	
	private final ThreadFactory factory;

	public ThreadFactoryAdapter(ThreadFactory factory) {
		super();
		this.factory = factory;
	}

	public Thread newThread(Runnable r) {
		return factory.newThread(r);
	}

	public Thread newThread(Runnable r, String name) {
		Thread thread = factory.newThread(r);
		thread.setName(name);
		return thread;
	}

	public Thread newThread(ThreadGroup group, Runnable r, String name) {
		Thread thread = factory.newThread(r);
		thread.setName(name);
		return thread;
	}

	@Override
	public void setPattern(String pattern) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIncludeClusterName(boolean includeClusterName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClusterName(String channelName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAddress(String address) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renameThread(String base_name, Thread thread) {
		// TODO Auto-generated method stub
		
	}

}
