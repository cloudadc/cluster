package com.kylin.tankwar.jgroups;

import org.apache.log4j.Logger;

import com.kylin.tankwar.core.Session;
import com.kylin.tankwar.jgroups.threads.AsychMissileThread;
import com.kylin.tankwar.jgroups.threads.AsychOtherThread;
import com.kylin.tankwar.jgroups.threads.AsychTankThread;


public class AsychCommunication extends Communication {
	
	public AsychCommunication(String jgroupsProps, String name) {
		super(jgroupsProps, name);
	}
	
	protected void startThreads() {
		
		logger.info("Start Thread");
		
		tankExecutor.execute(new AsychTankThread(tankQueue, getTankMap(), tankChannelName, tankClusterlName, jgroupsProps));
		missileExecutor.execute(new AsychMissileThread(missileQueue, getMissileMap(), missileChannelName, missileClusterName, jgroupsProps));
		otherExecutor.execute(new AsychOtherThread(otherQueue, explodes, getBlood(), otherChannelName, otherClusterName, jgroupsProps));
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

	@Override
	public String getChannelName() {
		// TODO Auto-generated method stub
		return null;
	}

	
	



	

}
