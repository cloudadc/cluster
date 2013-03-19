package com.kylin.tankwar;

import com.kylin.tankwar.core.MainFrame;
import com.kylin.tankwar.jgroups.AsychCommunication;


public class TankWar {

	public static void main(String[] args) {

		String props = "udp.xml";
		String name = null;
		boolean isGood = false ;
		
		for(int i=0; i < args.length; i++) {

			if (args[i].equals("-p")) {
				props = args[++i];
				continue;
			}

			if (args[i].equals("-n")) {
				name = args[++i];
				continue;
			}
			
			if (args[i].equals("isGood")) {
				isGood = true ;
				continue;
			}

			System.out.println("Run Application with [-p <props>] [-n <name>] [isGood]");
			System.exit(1);
		}
		
		AsychCommunication comm = new AsychCommunication(props, name);
		
		MainFrame mainFrame = new MainFrame(comm, isGood);
	}

}
