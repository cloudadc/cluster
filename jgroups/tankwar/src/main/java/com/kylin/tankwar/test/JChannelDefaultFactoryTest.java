package com.kylin.tankwar.test;

import com.kylin.tankwar.jgroups.factory.JChannelDefaultFactory;

public class JChannelDefaultFactoryTest {

	public static void main(String[] args) {

		JChannelDefaultFactory.newInstance().setJgroupsProps("udp.xml").createChannel("test", "test", null);
	}

}
