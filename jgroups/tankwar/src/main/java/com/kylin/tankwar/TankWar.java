package com.kylin.tankwar;

import com.kylin.tankwar.core.MainFrame;

public class TankWar {

	public static void main(String[] args) {

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

			System.out.println("Run Application with [-p <props>] [-n <name>] ");
			System.exit(1);
		}
		
		MainFrame mainFrame = new MainFrame(props, name);
	}

}
