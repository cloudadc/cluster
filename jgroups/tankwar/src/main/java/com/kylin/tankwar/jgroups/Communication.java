package com.kylin.tankwar.jgroups;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jgroups.JChannel;

import com.kylin.tankwar.core.ICommunication;
import com.kylin.tankwar.core.IJGroups;
import com.kylin.tankwar.core.IReplication;
import com.kylin.tankwar.core.ITank;
import com.kylin.tankwar.core.MainFrame;
import com.kylin.tankwar.core.Session;
import com.kylin.tankwar.core.Type;
import com.kylin.tankwar.model.BloodView;
import com.kylin.tankwar.model.ExplodeView;
import com.kylin.tankwar.model.MissileView;
import com.kylin.tankwar.model.Tank;
import com.kylin.tankwar.model.TankView;

public abstract class Communication implements ICommunication, IReplication, IJGroups, ITank{
	
	Map<String,Tank> tankMap = new ConcurrentHashMap<String,Tank>();
	
	public Map<String, Tank> getTankMap() {
		return tankMap;
	}
	
	public void put(String key, Tank value) {
		tankMap.put(key, value);
	}
	

	public void replicateTank(TankView view) {
		asychSend(new Session(view, Type.T));
	}

	public void replicateBlood(BloodView view) {
		asychSend(new Session(view, Type.B));
	}

	public void replicateExplode(ExplodeView view) {
		asychSend(new Session(view, Type.E));
	}

	public void replicateMissile(MissileView view) {
		asychSend(new Session(view, Type.M));
	}
	
	protected Executor tankExecutor, missileExecutor, otherExecutor ;
	protected ArrayBlockingQueue<Session> tankQueue, missileQueue, otherQueue ;
	
	protected String tankChannelName, missileChannelName, otherChannelName ;
	protected String tankClusterlName, missileClusterName, otherClusterName ;
	
	protected String jgroupsProps;
	
	public Communication(String jgroupsProps){
		
		this.jgroupsProps = jgroupsProps ;
		
		tankExecutor = Executors.newCachedThreadPool();
		missileExecutor = Executors.newCachedThreadPool();
		otherExecutor = Executors.newCachedThreadPool();
		
		tankQueue = new ArrayBlockingQueue<Session> (500);
		missileQueue = new ArrayBlockingQueue<Session> (500);
		otherQueue = new ArrayBlockingQueue<Session> (500);
		
		tankChannelName = "TankWar-Tank";
		missileChannelName = "TankWar-Missile";
		otherChannelName = "TankWar-Other";
		
		tankClusterlName = "TankWar-Tank-Cluster";
		missileClusterName = "TankWar-Missile-Cluster";
		otherClusterName = "TankWar-Other-Cluster";
		
		startThreads();
	}
	

	protected abstract void startThreads() ;

	protected static final String CLUSTER_NAME = "TankWarCluster";

	protected JChannel channel;
	
	protected MainFrame mainFrame;

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public Communication(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}

	
	public int getMemberSize() {
		
		if(null == channel) {
			return 0 ;
		} else {
			return channel.getView().getMembers().size();
		}
	}
	
	public String getChannelName() {
		if(null == channel) {
			return "";
		} else {
			return channel.getName();
		}
	}

	

	

}
