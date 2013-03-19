package com.kylin.tankwar.jgroups.threads;

import org.apache.log4j.Logger;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

public class ThreadBase extends ReceiverAdapter {
	
	private static final Logger logger = Logger.getLogger(ThreadBase.class);
	
	protected String name;
	protected String cluster;
	protected String jgroupsProps;
	
	public ThreadBase(String name, String cluster, String jgroupsProps){
		this.name = name ;
		this.cluster = cluster ;
		this.jgroupsProps = jgroupsProps;
	}

	public void viewAccepted(View view) {

		logger.info("** view: " + view);

		if (logger.isDebugEnabled()) {
			logger.debug(" -> View Id: " + view.getViewId());
			logger.debug(" -> View Creater: " + view.getCreator());
			logger.debug(" -> View Coordinator: " + view.getMembers().get(0));
			logger.debug(" -> View Memembers: " + view.getMembers());
		}

	}
}
