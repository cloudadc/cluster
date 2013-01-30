package com.kylin.tankwar.test;

import org.jgroups.JChannel;

import com.kylin.tankwar.jgroups.deprecated.ChannelFactory;
import com.kylin.tankwar.jgroups.deprecated.JChannelFactory;

public class FactoryTest {

	public static void main(String[] args) throws Exception {
		
		ChannelFactory factory = new JChannelFactory();
		factory.setMultiplexerConfig("jgroups-channelfactory-stacks.xml");
		
//		JChannel tcp = factory.createChannel("tcp");
		
		JChannel udp = factory.createChannel("udp");
		
		JChannel udp_async = factory.createChannel("udp-async");
		
		JChannel udp_sync = factory.createChannel("udp-sync");
		
		JChannel tcp_sync = factory.createChannel("tcp-sync");
		
	}

}
