package org.jgroups.demo.tankwar.jgroups;

import org.jgroups.Message;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.demo.tankwar.core.Session;


public class SynchCommunication extends Communication implements RequestHandler {

	public SynchCommunication(String jgroupsProps, String name) {
		super(jgroupsProps, name);
	}

	@Override
	public Session synchSend(Session session)
			throws TankWarCommunicationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void asychSend(Session session) throws TankWarCommunicationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object handle(Message msg) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void startThreads() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getChannelName() {
		// TODO Auto-generated method stub
		return null;
	}


}
