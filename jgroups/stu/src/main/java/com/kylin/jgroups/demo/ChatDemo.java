package com.kylin.jgroups.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.jgroups.JChannel;
import org.jgroups.Message;

import com.kylin.jgroups.DemoBase;
import com.kylin.jgroups.StartArray;

public class ChatDemo extends DemoBase {
	
	private static final Logger logger = Logger.getLogger(ChatDemo.class);
	
	private JChannel channel;

	public void start(String[] args) throws Exception {
		
		logger.info("Chat Demo Start");
		
		StartArray array = validation(args);
		
		channel = new JChannel(array.getProps());
		channel.setName(array.getName());
		channel.setReceiver(this);
		channel.connect(array.getClusterName());
		channel.setDiscardOwnMessages(true);
		eventLoop();
        channel.close();

	}

	private void eventLoop() {

		System.out.println("enter 'quit' or 'exit' exit the ChatDemo");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				String line = in.readLine().toLowerCase();
				if (line.startsWith("quit") || line.startsWith("exit")) {
					break;
				}
				Message msg = new Message(null, null, line);
				channel.send(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {

		new ChatDemo().start(args);
	}

}
