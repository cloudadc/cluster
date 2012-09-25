package com.kylin.tankwar.jgroups;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;

import com.kylin.tankwar.Explode;
import com.kylin.tankwar.Missile;
import com.kylin.tankwar.Tank;

public class TankWarCommunication extends ReceiverAdapter implements ICommunicate, RequestHandler, Serializable {
	
	private static final Logger logger = Logger.getLogger(TankWarCommunication.class);
	
	private static final String CLUSTER_NAME = "TankWarCluster";

	private JChannel channel;
	
	private MessageDispatcher disp;
	
	private Session session;
	
	private RspList rsp_list;
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void connect(String props, String name) {
		
		logger.info("Connect to Channel, props = " + props + ", name = " + name + ", clusterName = " + CLUSTER_NAME);
		
		try {
			channel = new JChannel(props);
			disp = new MessageDispatcher(channel, null, null, this);
			channel.setName(name);
			channel.setReceiver(this);
			channel.connect(CLUSTER_NAME);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	
	public void close() {
		
		try {
			channel.close();
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	
	public void send(Session session){
		
		setSession(session);
		
		try {
			byte[] buf = Util.objectToByteBuffer(session);
			channel.send(null, buf);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	
	public Session synSend(Session session) {
		
		setSession(session);
		
		try {
			Message msg = new Message(null, session);
			rsp_list = disp.castMessage(null, msg, new RequestOptions(ResponseMode.GET_ALL, 0));
			Session resp = (Session) rsp_list.getFirst();
			setSession(resp);
			return resp;
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	public void receive(Message msg) {
		
		logger.debug("Receive message");
		
		try {
			byte[] buf = msg.getBuffer();
			Session session = (Session) Util.objectFromByteBuffer(buf);
			setSession(session);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	public Object handle(Message msg) throws Exception {
		return msg.getObject();
	}

	public void viewAccepted(View view) {
		logger.debug("** view: " + view);
	}
	
	public static void main(String[] args) {

		TankWarCommunication comm = new TankWarCommunication();
		
		comm.connect("udp.xml", "Test");
		
		List<Explode> explodes = new ArrayList<Explode>();
		explodes.add(new Explode(10, 10, null));
		
//		comm.send(new Session(new ArrayList<Tank>(), new ArrayList<Missile>(), explodes));
//	
//		Session session = comm.getSession();
//		Explode e = session.getExplodes().get(0);
//		System.out.println("x = " + e.x + ", y = " + e.y);
		
		Session resp = comm.synSend(new Session(new ArrayList<Tank>(), new ArrayList<Missile>(), explodes));
		Explode e = resp.getExplodes().get(0);
		System.out.println("x = " + e.x + ", y = " + e.y);
	}

	

	

}
