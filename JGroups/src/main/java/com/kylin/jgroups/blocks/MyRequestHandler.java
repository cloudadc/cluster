package com.kylin.jgroups.blocks;

import org.apache.log4j.Logger;
import org.jgroups.Channel;
import org.jgroups.Message;
import org.jgroups.blocks.RequestHandler;

public class MyRequestHandler implements RequestHandler {
	
	private Channel channel;
	
	public MyRequestHandler(Channel channel) {
		super();
		this.channel = channel;
	}

	private static final Logger logger = Logger.getLogger(MyRequestHandler.class);

	public Object handle(Message msg) throws Exception {
		
		logger.debug("Handle Message");
		logger.debug("Message received, " + "** Message: " + msg + ", message content: [" + new String(msg.getBuffer()) + "]");
		logger.debug("Message Object: " + msg.getObject());
		logger.debug("Handle Message End");
		
		return channel.getName() + "-" + msg.getObject();
	}

}
