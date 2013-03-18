package com.kylin.tankwar.jgroups;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

	/**
	 * The session keep all group instance, sessions received from any member in group will be merged to this session.
	 */
//	Session session = new Session();
	
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
	
//	public Session getSession() {
//		return session;
//	}

	

	public abstract void connect(String props, String name);
	
	public abstract void close(); 

}
