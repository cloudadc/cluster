package com.kylin.tankwar.test;

import java.util.Random;
import java.util.UUID;

import org.jgroups.util.Util;

import com.kylin.tankwar.Direction;
import com.kylin.tankwar.core.TankView;
import com.kylin.tankwar.jgroups.AsychCommunication;
import com.kylin.tankwar.jgroups.Session;

/**
 * Use maven build project, copy jgroups-3.1.0.Final.jar and log4j-1.2.16.jar to target folder, use the following command can test the code
 *    java -cp jgroups-3.1.0.Final.jar:tankwar-1.0.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.tankwar.jgroups.test.AsychSendCommunicationTest -n node1
 * Usually we Run this test as a cluster, execute this command sequentially in 2 nodes, we will see session replication.
 * @author kylin
 *
 */
public class AsychSendCommunicationTest {

	public static void main(String[] args) {

		new AsychSendCommunicationTest().test(args);
	}
	
	private void test(String[] args) {
		
		String props = "udp.xml";
		String name = null;
		
		for(int i=0; i < args.length; i++) {

			if (args[i].equals("-p")) {
				props = args[++i];
				continue;
			}

			if (args[i].equals("-n")) {
				name = args[++i];
				continue;
			}

			exit();
		}
		
		AsychCommunication comm = new AsychCommunication();
		comm.connect(props, name);
		send(comm);
		
		while(true) {
			System.out.println();
			comm.getSession().print();
			Util.sleep(1000 * 5);
		}
	}

	private void send(AsychCommunication comm) {
		
		
		for(int i = 0 ; i < 5 ; i ++) {
			int value  = new Random().nextInt(1000);
			Session session = new Session();
			String uuid = UUID.randomUUID().toString();
			session.addTankDraw(uuid, new TankView(uuid, true, true, value, value, value, Direction.D, Direction.D));
			comm.asychSend(session);
			System.out.println("Response: \n");
			comm.getSession().print();
			Util.sleep(1000);
		}
		
		
	}

	private static void exit() {

		System.out.println("Run Application with [-p <props>] [-n <name>] ");

		System.exit(1);
	}

}
