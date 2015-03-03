package org.jgroups.demo.test.blocks;

import org.jgroups.Channel;

public class RpcMethods {
		
	private Channel channel;

	public RpcMethods(Channel channel) {
		super();
		this.channel = channel;
	}

	public Content updateContent(Content c) {
		System.out.println("Content before update: " + c);
		c.setName(c.getName() + "-" + channel.getName()).setViewId(c.getViewId() + "-" +channel.getView().getViewId());
		System.out.println("Content after update: " + c);
		return c ;
	}
	
	public String getNodeName(String name) {
		System.out.println(name);
		return channel.getName();
	}
}
