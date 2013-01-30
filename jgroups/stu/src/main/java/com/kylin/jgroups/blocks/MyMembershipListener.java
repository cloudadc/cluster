package com.kylin.jgroups.blocks;

import org.apache.log4j.Logger;
import org.jgroups.Address;
import org.jgroups.MembershipListener;
import org.jgroups.View;

public class MyMembershipListener implements MembershipListener {
	
	private static final Logger logger = Logger.getLogger(MyMembershipListener.class);

	public void viewAccepted(View new_view) {
		
		logger.info("ViewAccepted");
		logger.info(" -> View Id: " + new_view.getViewId());
		logger.info(" -> View Creater: " + new_view.getCreator());
		logger.info(" -> View Coordinator: " + new_view.getMembers().get(0));
		logger.info(" -> View Memembers: " + new_view.getMembers() + "\n");

	}

	public void suspect(Address suspected_mbr) {
		
		logger.info("suspect " + suspected_mbr);

	}

	public void block() {

		logger.info("block");
	}

	public void unblock() {

		logger.info("unblock");
	}

}
