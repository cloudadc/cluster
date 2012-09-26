package com.kylin.tankwar.jgroups.test;

import java.util.UUID;

import com.kylin.tankwar.Direction;
import com.kylin.tankwar.jgroups.Session;
import com.kylin.tankwar.jgroups.TankDraw;
import com.kylin.tankwar.jgroups.SynchCommunication;

/**
 * Use maven build project, copy jgroups-3.1.0.Final.jar and log4j-1.2.16.jar to target folder, use the following command can test the code
 *    java -cp jgroups-3.1.0.Final.jar:tankwar-1.0.jar:log4j-1.2.16.jar  -Djava.net.preferIPv4Stack=true com.kylin.tankwar.jgroups.test.SynchSendCommunicationTest -n node1
 * Usually we Run this test as a cluster, execute this command sequentially in 2 nodes, we will see session replication.
 * @author kylin
 *
 */
public class SynchSendCommunicationTest {

	public static void main(String[] args) {
		
		new SynchSendCommunicationTest().test(args);
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
		
		SynchCommunication comm = new SynchCommunication();
		comm.connect(props, name);
		send(comm);
	}

	private void send(SynchCommunication comm) {
		
		for(int i = 0 ; i < 5 ; i ++) {
			Session session = new Session();
			String uuid = UUID.randomUUID().toString();
			session.addTankDraw(uuid, new TankDraw(uuid, true, true, 1, 2, 2, Direction.D, Direction.D));
			System.out.println("Request: " + session.getTankDraw(uuid));
			Session resp = comm.synchSend(session);
			System.out.println("Response: " + resp.getTankDraw(uuid));
		}
	}

	private static void exit() {

		System.out.println("Run Application with [-p <props>] [-n <name>] ");

		System.exit(1);
	}

}
