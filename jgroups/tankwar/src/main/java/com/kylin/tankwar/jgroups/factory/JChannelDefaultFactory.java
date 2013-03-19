package com.kylin.tankwar.jgroups.factory;

import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;

import com.kylin.tankwar.jgroups.TankWarCommunicationException;

public class JChannelDefaultFactory implements ChannelFactory {
	
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

		try {
			JChannel channel = new JChannel(jgroupsProps);
			channel.setName(name);
			channel.setReceiver(reciever);
			channel.setDiscardOwnMessages(true);
			return channel ;
		} catch (Exception e) {
			throw new TankWarCommunicationException("connect to " + cluster + " error", e);
		}
		
	}

}
