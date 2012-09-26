package com.kylin.tankwar.jgroups;

import org.apache.log4j.Logger;
import org.jgroups.ReceiverAdapter;

public class AsychCommunication extends Communication {

	private static final long serialVersionUID = -1874030530018090586L;
	
	private static final Logger logger = Logger.getLogger(AsychCommunication.class);
	
	public void connect(String props, String name) {

		logger.info("Connect to Channel, props = " + props + ", name = " + name + ", clusterName = " + CLUSTER_NAME);
		
		//TODO
	}

	public void asychSend(Session session, ReceiverAdapter receive) throws TankWarCommunicationException {
		
		logger.debug("invoke asychronous session replication");
		
		//TODO
	}

	public void close() {

		logger.info("close channel " + channel.getName());
		
		//TODO
	}


	

}
