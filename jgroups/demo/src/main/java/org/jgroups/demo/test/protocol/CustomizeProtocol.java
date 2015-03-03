package org.jgroups.demo.test.protocol;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.conf.ClassConfigurator;

public class CustomizeProtocol {
	
	public static final short ID = 1900;

	public static void main(String[] args) throws Exception {

		JChannel ch = new JChannel();
		ch.connect("demo");
		ch.setReceiver(new ReceiverAdapter() {
			public void receive(Message msg) {
				MyHeader hdr = (MyHeader) msg.getHeader(ID);
				System.out.println("-- received " + msg + ", header is " + hdr);
			}
		});
		
		ClassConfigurator.addProtocol(ID, MyHeader.class);
		
		int cnt = 1;
		for (int i = 0; i < 5; i++) {
			Message msg = new Message();
			msg.putHeader(ID, new MyHeader(cnt++));
			ch.send(msg);
		}
		ch.close();
	}

}
