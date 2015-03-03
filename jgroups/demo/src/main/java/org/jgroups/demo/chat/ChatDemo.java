package org.jgroups.demo.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.management.MBeanServer;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.jmx.JmxConfigurator;
import org.jgroups.util.Util;

/**
 * What's this?
 *   Tests Basic jGroups API
 *   jGroups Chat Demo
 * 
 * How to Build?
 *   mvn clean install dependency:copy-dependencies
 *   
 * How to Run ChatDemo without bootstrap scripts?
 *   java -cp target/dependency/*:target/jgroups-demo.jar -Djava.net.preferIPv4Stack=true org.jgroups.demo.chat.ChatDemo -n node1    
 *   java -cp target/dependency/*:target/jgroups-demo.jar -Djava.net.preferIPv4Stack=true org.jgroups.demo.chat.ChatDemo -n node2
 *   
 * @author kylin
 *
 */
public class ChatDemo extends ReceiverAdapter{
	
	
	private JChannel channel;
	
	private void println(Object obj) {
		System.out.println(obj);
	}
	
	public void receive(Message msg) {
		Address sender = msg.getSrc();
		println("[" + sender + "] " + msg.getObject());
	}

	public void viewAccepted(View view) {
		println("view: " + view);
	}

	public void start(String props, String name, String clusterName, boolean isDiscardOwn) throws Exception {
		
		
		channel = new JChannel();
		if(null != name) {
			channel.setName(name);
		}
		channel.setReceiver(this);
		channel.setDiscardOwnMessages(true);
		channel.connect(clusterName);
		
		MBeanServer server = Util.getMBeanServer();
        if(server == null){
        	throw new Exception("No MBeanServers found;" + "\nLargeState needs to be run with an MBeanServer present, or inside JDK 5");
        }
        JmxConfigurator.registerChannel((JChannel)channel, server, "jgroups", channel.getClusterName(), true);
		
		eventLoop();
        channel.close();
	}

	private void eventLoop() {

		println("enter 'quit' or 'exit' exit the ChatDemo");
		
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
		
		String props = null, name = null, clusterName = null;
		boolean isDiscardOwn = false;
		for (int i = 0; i < args.length; i++) {
			 
			if (args[i].equals("-p")) {
				props = args[++i];
				continue;
			}
			
			if (args[i].equals("-n")) {
				name = args[++i];
				continue;
			}
			
			if (args[i].equals("-c")) {
				clusterName = args[++i];
				continue;
			}
			
			exit();
		}
		 
		
		if (null == clusterName) {
			clusterName = "ChatCluster";
		}
		
		if(null == props) {
			props = "udp.xml";
		}

		new ChatDemo().start(props, name, clusterName, isDiscardOwn);
	}

	private static void exit() {
		System.out.println("Run Application with [-n name]");
		System.exit(1);
	}

}
