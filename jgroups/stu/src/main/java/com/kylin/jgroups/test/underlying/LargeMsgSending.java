package com.kylin.jgroups.test.underlying;

import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

/**
 * 
 * How to build?
 *   mvn clean install dependency:copy-dependencies
 * 
 * How to run?
 *   java -cp dependency/*:jgroups-stu-1.0.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.test.underlying.LargeMsgSending
 * 
 * @author kylin
 *
 */
public class LargeMsgSending extends ReceiverAdapter {
	
	public void receive(Message msg) {
		System.out.println("    receive message: " + msg.getObject());
	}

	public void start(String props, String name) throws Exception {
		
		Channel channel = new JChannel(props);
		channel.setReceiver(this);
		
		if(name != null) {
			channel.setName(name);
		}
		
		channel.connect("LargeStateChannel");
		
		while(true){
			
			Msg msg = new Msg(10000);
			channel.send(null, msg);
			
			System.out.println("Press Enter to re-test\n");
			System.in.read();
		}
	}

	public static void main(String[] args) throws Exception {
		
		String props = "udp.xml";
		String name = "test";
		
		for (int i = 0; i < args.length; i++) {
			if ("-help".equals(args[i])) {
				help();
				return;
			}
		
			if ("-props".equals(args[i])) {
				props = args[++i];
				continue;
			}

			if ("-name".equals(args[i])) {
				name = args[++i];
				continue;
			}
			help();
			return;
		}
		
		new LargeMsgSending().start(props, name);
	}

	private static void help() {
		System.out.println("LargeMsgSending [-name <name>] " + "[-props <properties>] ");
	}

}
