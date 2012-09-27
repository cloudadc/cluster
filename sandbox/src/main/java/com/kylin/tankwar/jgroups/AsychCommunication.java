package com.kylin.tankwar.jgroups;

import org.apache.log4j.Logger;
import org.jgroups.JChannel;

public class AsychCommunication extends Communication {
	
	private static final Logger logger = Logger.getLogger(AsychCommunication.class);
	
	public AsychCommunication() {
		super();
	}

	public void connect(String props, String name) {

		logger.info("Connect to Channel, props = " + props + ", name = " + name + ", clusterName = " + CLUSTER_NAME);
		
		try {
			channel = new JChannel(props);
			
			if(null != name){
				channel.setName(name);
			}
			
			channel.setReceiver(new AsychReceiver(getSession()));
			channel.setDiscardOwnMessages(true);
			channel.connect(CLUSTER_NAME);
		} catch (Exception e) {
			TankWarCommunicationException tce = new TankWarCommunicationException("connect to " + CLUSTER_NAME + " error", e);
			logger.error(tce);
			throw tce;
		}
	}

	public void asychSend(Session session) throws TankWarCommunicationException {
		
		logger.debug("invoke asychronous session replication");
		
		getSession().merge(session);
		
		session.setNodeName(channel.getName());
		
		try {
			channel.send(null, session);
		} catch (Exception e) {
			TankWarCommunicationException tce = new TankWarCommunicationException("asychronous send session error", e);
			logger.error(tce);
			throw tce;
		}
		
	}

	public void close() {

		logger.info("close channel " + channel.getName());
		
		try {
			channel.close();
		} catch (Exception e) {
			TankWarCommunicationException tce = new TankWarCommunicationException("close channel error", e);
			logger.error(tce);
			throw tce;
		}
	}	

}
