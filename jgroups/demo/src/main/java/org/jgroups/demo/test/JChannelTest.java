package org.jgroups.demo.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jgroups.JChannel;
import org.jgroups.protocols.BARRIER;
import org.jgroups.protocols.FD_ALL;
import org.jgroups.protocols.FD_SOCK;
import org.jgroups.protocols.FRAG2;
import org.jgroups.protocols.MERGE2;
import org.jgroups.protocols.MFC;
import org.jgroups.protocols.PING;
import org.jgroups.protocols.UDP;
import org.jgroups.protocols.UFC;
import org.jgroups.protocols.UNICAST2;
import org.jgroups.protocols.VERIFY_SUSPECT;
import org.jgroups.protocols.pbcast.GMS;
import org.jgroups.protocols.pbcast.NAKACK;
import org.jgroups.protocols.pbcast.NAKACK2;
import org.jgroups.protocols.pbcast.STABLE;
import org.jgroups.stack.ProtocolStack;
import org.jgroups.util.UUID;

public class JChannelTest extends TestBase {
	

	public void test() throws Exception {
		
		System.out.println("'org.jgroups.JChannel' test");
		
		constructorTest();
		
		protocolStackTest();
		
		logicalNameTest();

	}

	private void logicalNameTest() throws Exception {

		JChannel channel = null;
		
		System.out.println("Logical Name test without set logical name");
		channel = new JChannel();
		channel.connect("ChatCluster");
		channel.close();
		
		System.out.println("Logical Name test while set logical name");
		channel = new JChannel();
		channel.setName(UUID.randomUUID().toString());
		channel.connect("ChatCluster");
		channel.close();
	}

	private void protocolStackTest() throws Exception {

		System.out.println("programmatically create a channel start");
		
		JChannel channel = new JChannel(false);
		ProtocolStack stack = new ProtocolStack();
		channel.setProtocolStack(stack);
		
		stack.addProtocols(new UDP().setValue("bind_addr", InetAddress.getByName("192.168.1.108")))
				.addProtocol(new PING())
				.addProtocol(new MERGE2())
				.addProtocol(new FD_SOCK())
				.addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
				.addProtocol(new VERIFY_SUSPECT()).addProtocol(new BARRIER())
				.addProtocol(new NAKACK()).addProtocol(new UNICAST2())
				.addProtocol(new STABLE()).addProtocol(new GMS())
				.addProtocol(new UFC()).addProtocol(new MFC())
				.addProtocol(new FRAG2());
		stack.init();
		
		
	}

	private void constructorTest() throws Exception {

		System.out.println("construct JChannel with udp.xml");
		
		JChannel channel = new JChannel("udp.xml");
		channel.close();
	}

	public static void main(String[] args) throws Exception {

		new JChannelTest().test();
	}

}
