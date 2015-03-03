package org.jgroups.demo.test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

/**
 * @author kylin
 * How to Run?
 * 1. build
 *    mvn clean dependency:copy-dependencies install
 * 2. run below command on 4 node   
 *    java -cp JGroups-stu.jar:jgroups-3.1.0.Final.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.jgroups.test.JChannelStateTest -n node1
 *    java -cp JGroups-stu.jar:jgroups-3.1.0.Final.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.jgroups.test.JChannelStateTest -n node2
 *    java -cp JGroups-stu.jar:jgroups-3.1.0.Final.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.jgroups.test.JChannelStateTest -n node3
 *    java -cp JGroups-stu.jar:jgroups-3.1.0.Final.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.jgroups.test.JChannelStateTest -n node4 get
 *
 */

public class JChannelStateTest extends ReceiverAdapter{

	private String name;

	private Boolean isGet = false;
	
	private JChannel channel ;

	public static void main(String[] args) throws Exception {

		new JChannelStateTest().test(args);
	}

	public void test(String[] args) throws Exception {
		
		System.out.println("JChannelStateTest start");

		validation(args);

		channel = new JChannel("udp.xml");
		channel.setName(name);
		channel.setReceiver(this);
		channel.setDiscardOwnMessages(true);
		channel.connect("JChannelGetStateTest");
		if(isGet) {
			channel.getState(null, 0);
		}
		eventLoop();
        channel.close();

	}
	
	private void eventLoop() {

		System.out.println("enter 'quit' or 'exit' exit the JChannelStateTest");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				String line = in.readLine().toLowerCase();
				if (line.startsWith("quit") || line.startsWith("exit")) {
					break;
				}
				Message msg = new Message(null, null, line);
				if(isGet) {
					channel.getState(null, 0);
				}
				channel.send(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void viewAccepted(View new_view) {
		System.out.println("** view: " + new_view);
	}

	public void receive(Message msg) {
		String line = "[" + msg.getSrc() + "]: " + msg.getObject();
		System.out.println(line);
	}

	List<String> state = new ArrayList<String>();
	
	public void getState(OutputStream output) throws Exception {
		
		System.out.println("getState() method invoked");
		state.add("JChannelStateTest test state provider is " + name);
		synchronized(state) {
			Util.objectToStream(state, new DataOutputStream(output));
		}
	}

	public void setState(InputStream input) throws Exception {
		
		System.out.println("setState() method invoked");
		
		List<String> list;
		list=(List<String>)Util.objectFromStream(new DataInputStream(input));
		synchronized (state) {
			state.clear();
			state.addAll(list);
		}
		System.out.println(list);
		
	}

	private void validation(String[] args) {
		
		if(args.length <= 0) {
			exit();
		}

		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("-n")) {
				name = args[++i];
				continue;
			}

			if (args[i].equals("get")) {
				isGet = true;
				continue;
			}

			exit();
		}
	}

	private void exit() {

		System.out.println("Run Application with [-n] <Node Name> [get]");
		Runtime.getRuntime().exit(0);
	}

}
