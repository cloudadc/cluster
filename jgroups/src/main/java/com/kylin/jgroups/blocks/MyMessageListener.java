package com.kylin.jgroups.blocks;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.jgroups.Message;
import org.jgroups.MessageListener;

public class MyMessageListener implements MessageListener {
	
	private static final Logger logger = Logger.getLogger(MyMessageListener.class);

	public void receive(Message msg) {

		logger.info("message received, " + "** Message: " + msg + ", message content: [" + new String(msg.getBuffer()) + "]");
	}

	public void getState(OutputStream output) throws Exception {

		logger.info("getState");
	}

	public void setState(InputStream input) throws Exception {
		
		logger.info("setState");
	}

}
