package com.kylin.tankwar.jgroups;

import org.apache.log4j.Logger;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import com.kylin.tankwar.core.Event;
import com.kylin.tankwar.core.MainFrame;
import com.kylin.tankwar.jgroups.handler.IHandler;

public class AsychReceiver extends ReceiverAdapter {
	
	private static final Logger logger = Logger.getLogger(AsychCommunication.class);

	private Session session;
	
	private MainFrame mainFrame;
	
	public AsychReceiver(Session session, MainFrame mainFrame) {
		super();
		this.session = session;
		this.mainFrame = mainFrame;
		
		logger.info("Initialized AsychReceiver");
	}

	public void receive(Message msg) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("handle message, " + msg.printHeaders() + " | " + msg.getSrc() + " | " + msg.toString()) ;
		}
		
		Session rec = (Session) msg.getObject();
		session.merge(rec);
		
		if(rec.getEvent() == Event.TM || rec.getEvent() == Event.TN) {
			mainFrame.getHandler().recieveHandler(mainFrame.getMyTank(), mainFrame.getTankMap(), session, mainFrame, rec.getEvent());
		} else if(rec.getEvent() == Event.MM || rec.getEvent() == Event.MN) {
			mainFrame.getHandler().recieveHandler(mainFrame.getMyTank().getId(), mainFrame.getMissileMap(), session, mainFrame);
		} else if(rec.getEvent() == Event.DEATH) {
			String missileId = rec.missileIdSet().iterator().next() ;
			String tankId = rec.tankIdSet().iterator().next() ;
			mainFrame.getHandler().recieveHandler(missileId, tankId, session, mainFrame);
		}
		
		//--TODO Explode
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
