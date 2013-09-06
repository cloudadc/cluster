package org.jgroups.demo.tankwar.jgroups.factory;

import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;

public interface ChannelFactory {

	Channel createChannel(String id) throws Exception;
	
	ProtocolStackConfiguration getProtocolStackConfiguration();
	
	public JChannel createChannel(String name, String cluster,  ReceiverAdapter reciever);
}
