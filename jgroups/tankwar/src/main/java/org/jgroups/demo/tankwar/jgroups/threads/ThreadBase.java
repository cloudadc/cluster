package org.jgroups.demo.tankwar.jgroups.threads;

import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.demo.tankwar.jgroups.factory.JChannelDefaultFactory;


public class ThreadBase extends ReceiverAdapter {
		
	protected String name;
	protected String cluster;
	protected String jgroupsProps;
	
	protected JChannel channel;
	
	public ThreadBase(String name, String cluster, String jgroupsProps){
		this.name = name ;
		this.cluster = cluster ;
		this.jgroupsProps = jgroupsProps;
		
		channel = JChannelDefaultFactory.newInstance().setJgroupsProps(jgroupsProps).createChannel(name, cluster, this);
	}

	public void viewAccepted(View view) {

		System.out.println("** view: " + view);

	}
	
	public boolean isCoordinator() {
		return channel.getView().getCreator().equals(channel.getView().getMembers().get(0));
	}
}
