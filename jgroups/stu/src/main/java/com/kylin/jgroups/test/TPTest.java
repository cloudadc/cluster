package com.kylin.jgroups.test;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.protocols.TP;
import org.jgroups.util.Util;


/**
 * 
 * How to build?
 *   mvn clean install dependency:copy-dependencies
 * 
 * How to run?
 *   java -cp jgroups-3.2.6.Final.jar:jgroups-stu.jar:log4j-1.2.16.jar -Djava.net.preferIPv4Stack=true com.kylin.jgroups.test.TPTest tankwar-udp.xml  
 *   java -cp ./target/jgroups-stu.jar:./target/dependency/* -Djava.net.preferIPv4Stack=true com.kylin.jgroups.test.TPTest tankwar-udp.xml
 * 
 * @author kylin
 *
 */
public class TPTest {

	public static void main(String[] args) throws Exception {

		JChannel channel = new JChannel(args[0]);
		
		channel.setReceiver(new ReceiverAdapter(){

			public void receive(Message msg) {
				Address sender=msg.getSrc();
				System.out.println(msg.getObject() + " [" + sender + "]");
			}

			public void viewAccepted(View view) {
				System.out.println("view: " + view);
			}});
		
		channel.connect("TPTest");
		
		TP transport = channel.getProtocolStack().getTransport();
		
		System.out.println("-------------- JGroups Protocol Stack Transport ----------------------");
		System.out.println("transport.getInfo(): " + transport.getInfo());
		System.out.println("transport.isSingleton(): " + transport.isSingleton());
		System.out.println("transport.getBindAddress(): " + transport.getBindAddress());
		System.out.println("transport.getBindPort(): " + transport.getBindPort());
		System.out.println("----------------------------------------------------------------------");
		
		for (;;) {
			String line = Util.readStringFromStdin(": ");
			channel.send(null, line);
		}
	}

}
