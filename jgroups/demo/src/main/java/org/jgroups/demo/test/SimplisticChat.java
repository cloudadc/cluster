package org.jgroups.demo.test;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

public class SimplisticChat {

	public static void main(String[] args) throws Exception {

		JChannel channel = new JChannel();
		
		channel.setReceiver(new ReceiverAdapter(){

			public void receive(Message msg) {
				Address sender = msg.getSrc();
				System.out.println(msg.getObject() + " [" + sender + "]");
			}

			public void viewAccepted(View view) {
				System.out.println("view: " + view);
			}});
		
		channel.connect("ChatCluster");
		
		System.out.println(channel.getView().getCreator().equals(channel.getView().getMembers().get(0)));
		
		for (;;) {
			String line = Util.readStringFromStdin(": ");
			channel.send(null, line);
		}
	}

}
