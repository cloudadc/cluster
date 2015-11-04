package org.jgroups.demo.test.blocks;

import java.io.InputStream;
import java.io.OutputStream;

import org.jgroups.Message;
import org.jgroups.MessageListener;

public class MyMessageListener implements MessageListener {
	

	public void receive(Message msg) {
		System.out.println("message received, " + "sender: " + msg.getSrc() + ", content: " + msg.getObject());
	}

	public void getState(OutputStream output) throws Exception {
		System.out.println("getState");
	}

	public void setState(InputStream input) throws Exception {
		System.out.println("setState");
	}

}
