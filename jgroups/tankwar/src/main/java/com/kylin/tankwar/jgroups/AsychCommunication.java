package com.kylin.tankwar.jgroups;

import org.apache.log4j.Logger;

import com.kylin.tankwar.core.Session;
import com.kylin.tankwar.jgroups.threads.AsychTankThread;


public class AsychCommunication extends Communication {
	
	public AsychCommunication(String jgroupsProps) {
		super(jgroupsProps);
	}
	
	protected void startThreads() {
		
		tankExecutor.execute(new AsychTankThread(tankQueue, getTankMap(), tankChannelName, tankClusterlName, jgroupsProps));
	}	
	

	private static final Logger logger = Logger.getLogger(AsychCommunication.class);


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
	



	

}
