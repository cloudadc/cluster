package org.jgroups.demo.tankwar.jgroups;

import org.jgroups.demo.tankwar.core.Session;
import org.jgroups.demo.tankwar.jgroups.threads.AsychMissileThread;
import org.jgroups.demo.tankwar.jgroups.threads.AsychOtherThread;
import org.jgroups.demo.tankwar.jgroups.threads.AsychTankThread;



public class AsychCommunication extends Communication {
	
	public AsychCommunication(String jgroupsProps, String name) {
		super(jgroupsProps, name);
	}
	
	protected void startThreads() {
		
		System.out.println("Start Thread");
		
		tankExecutor.execute(new AsychTankThread(tankQueue, getTankMap(), tankChannelName, tankClusterlName, jgroupsProps));
		missileExecutor.execute(new AsychMissileThread(missileQueue, getMissileMap(), missileChannelName, missileClusterName, jgroupsProps));
		otherExecutor.execute(new AsychOtherThread(otherQueue, explodes, getBloods(), otherChannelName, otherClusterName, jgroupsProps));
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
	public String getChannelName() {
		// TODO Auto-generated method stub
		return null;
	}

	
	



	

}
