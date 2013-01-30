package com.kylin.tankwar.jgroups.factory;

import org.jgroups.Channel;

public interface ChannelFactory {

	Channel createChannel(String id) throws Exception;
	
	ProtocolStackConfiguration getProtocolStackConfiguration();
}
