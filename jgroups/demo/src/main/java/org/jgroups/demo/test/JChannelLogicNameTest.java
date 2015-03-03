package org.jgroups.demo.test;

import org.jgroups.JChannel;

public class JChannelLogicNameTest {

	public static void main(String[] args) throws Exception {

		JChannel channel = new JChannel("udp.xml");
//		channel.setName("JBoss Cluster");
//		channel.connect("JChannelLogicNameTest");
//		System.out.println(channel.getAddress());
//		System.out.println(channel.getClusterName());
		System.out.println(channel.getView());
	}

}
