package com.kylin.jgroups.blocks;

import org.apache.log4j.Logger;
import org.jgroups.Channel;

public class RpcMethods {
	
	private static final Logger logger = Logger.getLogger(RpcMethods.class);
	
	private Channel channel;

	public RpcMethods(Channel channel) {
		super();
		this.channel = channel;
	}

	public Content updateContent(Content c) {
		logger.info("Content before update: " + c);
		c.setName(c.getName() + "-" + channel.getName()).setViewId(c.getViewId() + "-" +channel.getView().getViewId());
		logger.info("Content after update: " + c);
		return c ;
	}
	
	public String getNodeName(String name) {
		logger.info(name);
		return channel.getName();
	}
}
