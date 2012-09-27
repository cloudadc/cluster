package com.kylin.tankwar.jgroups;

import org.apache.log4j.Logger;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

public class AsychReceiver extends ReceiverAdapter {
	
	private static final Logger logger = Logger.getLogger(AsychCommunication.class);

	private Session session;
	
	public AsychReceiver(Session session) {
		super();
		this.session = session;
		
		logger.info("Initialized AsychReceiver");
	}

	public void receive(Message msg) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("handle message, " + msg.printHeaders() + " | " + msg.getSrc() + " | " + msg.toString()) ;
		}
		
		Session rec = (Session) msg.getObject();
		session.merge(rec);
		
	}

	public void viewAccepted(View view) {
		
		logger.info("** view: " + view);
		
		if(logger.isDebugEnabled()) {
			logger.debug(" -> View Id: " + view.getViewId());
			logger.debug(" -> View Creater: " + view.getCreator());
			logger.debug(" -> View Coordinator: " + view.getMembers().get(0));
			logger.debug(" -> View Memembers: " + view.getMembers());
		}
		
	}

}
