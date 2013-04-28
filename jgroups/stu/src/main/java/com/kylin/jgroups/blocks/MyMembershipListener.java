package com.kylin.jgroups.blocks;

import org.apache.log4j.Logger;
import org.jgroups.Address;
import org.jgroups.MembershipListener;
import org.jgroups.View;

public class MyMembershipListener implements MembershipListener {
	
	private static final Logger logger = Logger.getLogger(MyMembershipListener.class);

	public void viewAccepted(View view) {
		logger.info("ViewAccepted, " + view);
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
