package com.kylin.tankwar.jgroups.factory;

import org.apache.log4j.Logger;
import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;

import com.kylin.tankwar.jgroups.TankWarCommunicationException;

public class JChannelDefaultFactory implements ChannelFactory {
	
	private static final Logger logger = Logger.getLogger(JChannelDefaultFactory.class);
	
	private static JChannelDefaultFactory instance = null;
	
	public static JChannelDefaultFactory newInstance() {
		if(null == instance) {
			instance = new JChannelDefaultFactory();
		}
		return instance ;
	}
	
	private JChannelDefaultFactory() {}
	
	String jgroupsProps = "tankwar-udp.xml";

	public JChannelDefaultFactory setJgroupsProps(String jgroupsProps) {
		this.jgroupsProps = jgroupsProps;
		return this ;
	}

	public Channel createChannel(String id) throws Exception {
		return null;
	}

	public ProtocolStackConfiguration getProtocolStackConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	public JChannel createChannel(String name, String cluster, ReceiverAdapter reciever) {

		logger.info("Create JGroups Channel, name = " + name + ", cluster = " + cluster);
		
		try {
			JChannel channel = new JChannel(jgroupsProps);
			channel.setName(name);
			channel.setReceiver(reciever);
			channel.setDiscardOwnMessages(true);
			channel.connect(cluster);
			return channel ;
		} catch (Exception e) {
			throw new TankWarCommunicationException("connect to " + cluster + " error", e);
		}
		
	}

}
