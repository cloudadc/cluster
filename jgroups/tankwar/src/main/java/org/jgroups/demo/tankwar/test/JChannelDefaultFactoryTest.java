package org.jgroups.demo.tankwar.test;

import org.jgroups.demo.tankwar.jgroups.factory.JChannelDefaultFactory;

public class JChannelDefaultFactoryTest {

	public static void main(String[] args) {

		JChannelDefaultFactory.newInstance().setJgroupsProps("udp.xml").createChannel("test", "test", null);
	}

}
