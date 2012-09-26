package com.kylin.tankwar.jgroups;

import org.apache.log4j.Logger;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.util.RspList;


public class SynchCommunication extends Communication implements RequestHandler {

	private static final long serialVersionUID = 8037392995230775306L;

	private static final Logger logger = Logger.getLogger(SynchCommunication.class);
	
	private MessageDispatcher msgDispatcher;
	
	private RspList<Session> rsp_list;
	
	public void connect(String props, String name) {
		
		logger.info("Connect to Channel, props = " + props + ", name = " + name + ", clusterName = " + CLUSTER_NAME);
		
		try {
			channel = new JChannel(props);
			msgDispatcher = new MessageDispatcher(channel, null, null, this);
			
			if(null != name){
				channel.setName(name);
			}
			
			channel.connect(CLUSTER_NAME);
		} catch (Exception e) {
			TankWarCommunicationException tce = new TankWarCommunicationException("connect to " + CLUSTER_NAME + " error", e);
			logger.error(tce);
			throw tce;
		}
	}
	
	public void close() {
		
		logger.info("close channel " + channel.getName());
		
		try {
			channel.close();
			
			if(null != msgDispatcher) {
				msgDispatcher.stop();
			}
		} catch (Exception e) {
			TankWarCommunicationException tce = new TankWarCommunicationException("close channel error", e);
			logger.error(tce);
			throw tce;
		}
	}
	
	/**
	 * merge origin session and received session
	 */
	public Object handle(Message msg) throws Exception {
		
		if(logger.isDebugEnabled()) {
			logger.debug("handle message, " + msg.printHeaders() + " | " + msg.getSrc() + " | " + msg.toString()) ;
		}
		
		Session resp = (Session) msg.getObject();
		resp.merge(getSession());
		
		return msg.getObject();
	}

	public Session synchSend(Session session) throws TankWarCommunicationException {
		
		logger.debug("invoke synchronous session replication");
		
		setSession(session);
		
		try {
			Message msg = new Message(null, session);
			rsp_list = msgDispatcher.castMessage(null, msg, new RequestOptions(ResponseMode.GET_ALL, 0));
			Session resp = rsp_list.getFirst();
			return resp;
		} catch (Exception e) {
			TankWarCommunicationException tce = new TankWarCommunicationException("synchronous send session error", e);
			logger.error(tce);
			throw tce;
		}
		
	}

}
