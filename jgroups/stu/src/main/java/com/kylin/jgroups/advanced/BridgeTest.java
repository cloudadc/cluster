package com.kylin.jgroups.advanced;

import org.jgroups.JChannel;

public class BridgeTest {
	
	static {
		System.setProperty("java.net.preferIPv4Stack", "true");
	}

	public static void main(String[] args) throws Exception {

		JChannel channel = new JChannel("jgroups-config.xml");
		channel.connect("BridgeTestCluster");
	}

}
