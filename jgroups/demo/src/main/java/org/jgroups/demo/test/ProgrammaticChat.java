package org.jgroups.demo.test;

import java.net.InetAddress;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
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
import org.jgroups.protocols.pbcast.STABLE;
import org.jgroups.stack.ProtocolStack;
import org.jgroups.util.Util;

public class ProgrammaticChat {
	
	static final String BIND_ADDR = "192.168.1.101" ;

	public static void main(String[] args) throws Exception {

		JChannel channel = new JChannel(false);
		ProtocolStack stack = new ProtocolStack();
		channel.setProtocolStack(stack);
		
		stack.addProtocols(new UDP().setValue("bind_addr", InetAddress.getByName(BIND_ADDR)))
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
		
		channel.setReceiver(new ReceiverAdapter(){

			public void receive(Message msg) {
				Address sender=msg.getSrc();
				System.out.println(msg.getObject() + " [" + sender + "]");
			}

			public void viewAccepted(View view) {
				System.out.println("view: " + view);
			}});
		
		channel.connect("ChatCluster");
		
		for (;;) {
			String line = Util.readStringFromStdin(": ");
			channel.send(null, line);
		}

	}

}
