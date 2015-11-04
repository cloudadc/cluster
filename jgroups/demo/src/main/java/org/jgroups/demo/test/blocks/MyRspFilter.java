package org.jgroups.demo.test.blocks;

import org.jgroups.Address;
import org.jgroups.blocks.RspFilter;

public class MyRspFilter implements RspFilter {
	
	public boolean isAcceptable(Object response, Address sender) {
		String name = (String) response;
		if(name.equals("node2")) {
			return false;
		}else {
			return true;
		}
	}

	public boolean needMoreResponses() {
		return true;
	}

}
