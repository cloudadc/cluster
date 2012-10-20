package com.kylin.jgroups.blocks;

import org.apache.log4j.Logger;
import org.jgroups.Address;
import org.jgroups.blocks.RspFilter;

public class MyRspFilter implements RspFilter {

	private static final Logger logger = Logger.getLogger(MyRspFilter.class);
	
	public boolean isAcceptable(Object response, Address sender) {
		
		logger.info("isAcceptable(), response: " + response + ", sender: " + sender);
		
		Content c = (Content) response;
		
		return c.getId() % 2 == 0;
	}

	public boolean needMoreResponses() {
		
		logger.info("set needMoreResponses valuse is true");
		
		return true;
	}

}
