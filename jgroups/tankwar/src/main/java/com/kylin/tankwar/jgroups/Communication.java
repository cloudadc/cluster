package com.kylin.tankwar.jgroups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.kylin.tankwar.core.ICommunication;
import com.kylin.tankwar.core.IJGroups;
import com.kylin.tankwar.core.IReplication;
import com.kylin.tankwar.core.ITank;
import com.kylin.tankwar.core.Session;
import com.kylin.tankwar.core.Type;
import com.kylin.tankwar.model.Blood;
import com.kylin.tankwar.model.BloodView;
import com.kylin.tankwar.model.Explode;
import com.kylin.tankwar.model.ExplodeView;
import com.kylin.tankwar.model.Missile;
import com.kylin.tankwar.model.MissileView;
import com.kylin.tankwar.model.Tank;
import com.kylin.tankwar.model.TankView;

public abstract class Communication implements ICommunication, IReplication, IJGroups, ITank{
	
	private final static Logger logger = Logger.getLogger(Communication.class);
	
	Map<String,Tank> tankMap = new ConcurrentHashMap<String,Tank>();
	
	public Map<String, Tank> getTankMap() {
		return tankMap;
	}
	
	public void put(String key, Tank value) {
		tankMap.put(key, value);
	}
	
	Map<String, Missile> missileMap = new ConcurrentHashMap<String, Missile>();
	
	public Map<String, Missile> getMissileMap() {
		return missileMap;
	}
	
	public void put(String key, Missile value){
		missileMap.put(key, value);
	}
	
	List<Explode> explodes = Collections.synchronizedList(new ArrayList<Explode>()); 
	
	public void add(Explode explode) {
		explodes.add(explode);
	}

	public List<Explode> getExplodes() {
		return explodes;
	}
	
	private Blood blood;

	public Blood getBlood() {
		return blood;
	}

	public void setBlood(Blood blood) {
		this.blood = blood;
	}

	public String getName() {
		return name ;
	}
	
	public int getMemberSize() {	
		return getTankMap().size();
	}
	
	public void replicateTank(TankView view) {
		try {
			tankQueue.put(new Session(view, Type.T));
		} catch (InterruptedException e) {
			throw new TankWarCommunicationException("replicate Tank Error", e);
		}
	}

	public void replicateBlood(BloodView view) {
		try {
			otherQueue.put(new Session(view, Type.B));
		} catch (InterruptedException e) {
			throw new TankWarCommunicationException("replicate Blood Error", e);
		}
	}

	public void replicateExplode(ExplodeView view) {
		try {
			otherQueue.put(new Session(view, Type.E));
		} catch (InterruptedException e) {
			throw new TankWarCommunicationException("replicate Explode Error", e);
		}
	}

	public void replicateMissile(MissileView view) {
		try {
			missileQueue.put(new Session(view, Type.M));
		} catch (InterruptedException e) {
			throw new TankWarCommunicationException("replicate Missile Error", e);
		}
	}
	
	protected Executor tankExecutor, missileExecutor, otherExecutor ;
	protected ArrayBlockingQueue<Session> tankQueue, missileQueue, otherQueue ;
	
	protected String tankChannelName, missileChannelName, otherChannelName ;
	protected String tankClusterlName, missileClusterName, otherClusterName ;
	
	protected String jgroupsProps;
	private String name;
	
	public Communication(String jgroupsProps, String name){
		
		this.jgroupsProps = jgroupsProps ;
		this.name = name;
		
		tankExecutor = Executors.newCachedThreadPool();
		missileExecutor = Executors.newCachedThreadPool();
		otherExecutor = Executors.newCachedThreadPool();
		logger.info("initialized ThreadPools");
		
		tankQueue = new ArrayBlockingQueue<Session> (500);
		missileQueue = new ArrayBlockingQueue<Session> (500);
		otherQueue = new ArrayBlockingQueue<Session> (500);
		logger.info("initialized blocking queue");
		
		tankChannelName = name + "-Tank";
		missileChannelName = name + "-Missile";
		otherChannelName = name + "-Other";
		
		tankClusterlName = "TankWar-Tank-Cluster";
		missileClusterName = "TankWar-Missile-Cluster";
		otherClusterName = "TankWar-Other-Cluster";
				
		startThreads();
	}
	

	protected abstract void startThreads() ;

}
