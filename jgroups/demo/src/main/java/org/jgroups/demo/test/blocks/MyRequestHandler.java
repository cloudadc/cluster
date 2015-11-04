package org.jgroups.demo.test.blocks;

import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.Message;
import org.jgroups.blocks.RequestHandler;

public class MyRequestHandler implements RequestHandler {
	
	private Channel channel;
	
	public MyRequestHandler(Channel channel) {
		super();
		this.channel = channel;
	}

	public Object handle(Message msg) throws Exception {
		Address sender = msg.getSrc();
		System.out.println(sender + ", " + msg.getObject());
		return channel.getName() + "-" + msg.getObject();
	}

}
