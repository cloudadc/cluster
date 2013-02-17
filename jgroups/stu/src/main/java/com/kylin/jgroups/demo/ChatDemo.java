package com.kylin.jgroups.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.management.MBeanServer;

import org.apache.log4j.Logger;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.jmx.JmxConfigurator;
import org.jgroups.util.Util;

/**
 * What's this?
 *   Tests for JGroups underlying Thread pool, Thread group
 *   Tests for Protocol Stack
 * 
 * How to Build?
 *   mvn clean install dependency:copy-dependencies
 *   
 * How to Run?
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.demo.ChatDemo -n node1 -p udp.xml -discardOwn    
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.demo.ChatDemo -n node2 -p udp.xml -discardOwn
 * 
 * @author kylin
 *
 */
public class ChatDemo extends ReceiverAdapter{
	
	private static final Logger logger = Logger.getLogger(ChatDemo.class);
	
	private JChannel channel;
	
	public void start(String props, String name, String clusterName, boolean isDiscardOwn) throws Exception {
		
		logger.info("Chat Demo Start");
		
		channel = new JChannel(props);
		
		if(null != name) {
			channel.setName(name);
		}
		
		channel.setReceiver(this);
		
		if(isDiscardOwn) {
			channel.setDiscardOwnMessages(true);
		}
		
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
			
			if (args[i].equals("-discardOwn")) {
				isDiscardOwn = true ;
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
		System.out.println("Run Application with [-p props] [-n name] [-c clusterName] [-discardOwn]");
		System.exit(1);
	}

}
