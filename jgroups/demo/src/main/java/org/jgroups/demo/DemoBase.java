package org.jgroups.demo;

import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.demo.test.StartArray;

public abstract class DemoBase extends ReceiverAdapter {
	
	public abstract void start(String[] args) throws Exception;
	
	public void viewAccepted(View new_view) {
		System.out.println("** view: " + new_view);
	}

	public void receive(Message msg) {
		String line = "[" + msg.getSrc() + "]: " + msg.getObject();
		System.out.println(line);
	}
	
	public StartArray validation(String[] args) {
		
		StartArray array = new StartArray();
		
		 for(int i=0; i < args.length; i++) {
			 
			if (args[i].equals("-p")) {
				array.setProps(args[++i]);
				continue;
			}
			
			if (args[i].equals("-n")) {
				array.setName(args[++i]);
				continue;
			}
			
			if (args[i].equals("-c")) {
				array.setClusterName(args[++i]);
				continue;
			}
			
			exit();
		}
		 
		if (null == array.getName()) {
			array.setName("KylinSoongTest");
		}
		
		if (null == array.getClusterName()) {
			array.setName("KylinSoongTestCluster");
		}
		
		if(null == array.getProps()) {
			array.setProps("udp.xml");
		}
		 
		return array;
	}

	private void exit() {
		
		System.out.println("Run Application with [-p props] [-n name] [-c clusterName]");

		System.exit(1);
	}
}
