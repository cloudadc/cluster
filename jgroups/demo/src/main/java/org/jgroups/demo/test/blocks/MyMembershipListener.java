package org.jgroups.demo.test.blocks;

import org.jgroups.Address;
import org.jgroups.MembershipListener;
import org.jgroups.View;

public class MyMembershipListener implements MembershipListener {
	
	public void viewAccepted(View view) {
		System.out.println("ViewAccepted, " + view);
	}

	public void suspect(Address suspected_mbr) {
		
		System.out.println("suspect " + suspected_mbr);

	}

	public void block() {

		System.out.println("block");
	}

	public void unblock() {

		System.out.println("unblock");
	}

}
