package org.jgroups.demo.tankwar.jgroups.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

public class SimpleChat extends ReceiverAdapter{
	
	JChannel channel;
	
	private void start() throws Exception {
		channel = new JChannel();
		channel.setReceiver(this);
		channel.connect("ChatCluster");
		eventLoop();
		channel.close();
	}



	private void eventLoop() {
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				System.out.print("> ");
				System.out.flush();
				String line = in.readLine().toLowerCase();
				if (line.startsWith("quit") || line.startsWith("exit")) {
					break;
				}
				line = "[kylin] " + line;
				Message msg = new Message(null, null, line);
				channel.send(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}


	public void receive(Message msg) {
		System.out.println("&& Receive: " + msg.getSrc() + ": " + msg.getObject());
	}

	public void viewAccepted(View view) {
		System.out.println("** view: " + view);

	}



	public static void main(String[] args) throws Exception {

		new SimpleChat().start();
	}

}
